package com.fy.base.dialog

import android.content.Context
import androidx.core.view.isVisible
import com.fy.extension.globalExt.getActivity
import com.fy.base.databinding.LayoutDialogFragmentBinding

class LoadingDialog(
    private val context: Context,
    private val message: String
) : BaseDialog<LayoutDialogFragmentBinding>(context) {

    override fun initView() {
        binding?.dialogText?.run {
            isVisible = message.isNotEmpty()
            text = message
        }
    }

    init {
        initDialog()
    }

    override fun getViewBinding() = context.getActivity()?.layoutInflater?.let { inf ->
        LayoutDialogFragmentBinding.inflate(inf)
    }
}