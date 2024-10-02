package com.fy.main

import android.os.Bundle
import android.util.Log
import android.view.View
import com.fy.base.BaseFragment
import com.fy.extension.globalExt.viewBinding
import com.fy.main.navigate.MainNavigation
import com.fy.main.navigate.MainNavigationImpl
import com.fy.ui.R
import com.fy.ui.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.fy.extension.globalExt.launchRepeatWithViewLifecycle
import com.fy.utils.enum.Screen
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainNavigation, MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)
    override val navigator: MainNavigation = MainNavigationImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collect()
    }

    private fun collect() = launchRepeatWithViewLifecycle {
        launch {
            viewModel.movieUiState.collect { state ->
                when (state) {
                    is MovieUiState.SuccessMovieList -> {
                       // Log.e("TAG", state.list.toMutableList().toString())
                    }

                    is MovieUiState.RefreshMovieList -> {
                        //no*op
                    }

                    else -> {
                        //no*op
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieList()
    }

    override fun getScreens()= Screen.MAIN
}