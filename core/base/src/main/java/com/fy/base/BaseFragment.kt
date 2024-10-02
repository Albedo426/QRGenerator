package com.fy.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fy.base.delegation.HandleErrorDelegation
import com.fy.base.delegation.HandleErrorDelegationImpl
import com.fy.extension.globalExt.launchRepeatWithViewLifecycle
import com.fy.utils.model.NetworkState
import com.fy.navigation.Navigation
import com.fy.utils.enum.Screen
import kotlinx.coroutines.launch
import timber.log.Timber


abstract class BaseFragment<NAV : Navigation, out VM : BaseViewModel>(
    @LayoutRes resId: Int
) : Fragment(resId), HandleErrorDelegation by HandleErrorDelegationImpl() {

    abstract val navigator: NAV
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.bind(findNavController())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachViewModels(viewModel)
    }

    override fun onResume() {
        super.onResume()
        fragmentNameFinder()
    }

    private fun fragmentNameFinder() = Timber.d(
        "currentFragment: ${this::class.java.simpleName}\n" + "currentFragmentPackage: ${this::class.java}"
    )


    private fun attachViewModels(vararg viewModels: BaseViewModel) {
        launchRepeatWithViewLifecycle {
            viewModels.forEach { viewModel ->
                launch {
                    viewModel.networkSharedFlow.collect { state ->
                        when (state) {
                            is NetworkState.Initial -> {
                                // no-op
                            }

                            is NetworkState.Error -> {
                                errorDelegation(
                                    fragment = this@BaseFragment, errorState = state
                                )
                            }

                            is NetworkState.Failure -> {
                                with(state) {
                                    handleFailureDelegation(
                                        fragment = this@BaseFragment,
                                        statusCode = error.statusCode,
                                        message = error.message,
                                        messageType = messageType,
                                        payload = error.payload
                                    )
                                }
                            }

                            is NetworkState.Loading -> {
                                requireActivity().runOnUiThread {
                                    viewLifecycleOwner.lifecycleScope.launch {
                                        handleLoadingDelegation(
                                            requireContext(),
                                            state.loadingType,
                                            state.loadingText.orEmpty()
                                        )
                                    }
                                }

                            }

                            is NetworkState.Success -> {
                                handleSuccessDelegation(
                                    fragment = this@BaseFragment,
                                    message = state.response.message.orEmpty(),
                                    messageType = state.messageType
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    abstract fun getScreens(): Screen
    override fun onDestroy() {
        super.onDestroy()
    }
}
