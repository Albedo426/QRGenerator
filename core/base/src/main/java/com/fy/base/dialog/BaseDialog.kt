package com.fy.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Display
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.fy.extension.globalExt.getActivity
import com.fy.extension.orFalse

abstract class BaseDialog<VB : ViewBinding?>(
    private var context: Context
) : DialogInterface {

    private var dialog: Dialog? = null

    private var width = 0

    private var height = 0

    private var mCancelable = false

    var isHidden = false

    protected abstract fun initView()

    protected var binding: VB?=null
    abstract fun getViewBinding(): VB?

    override fun showDialog() {
        isHidden = false
        try {
            val activity = context.getActivity()
            if (dialog != null && !(activity != null && activity.isFinishing && dialog?.isShowing.orFalse()))
                dialog?.show()
        } catch (e: WindowManager.BadTokenException) {
            Log.i("hide dialog", "illegal")
        }
    }

    override fun hideDialog() {
        isHidden = true
        try {
            val activity = context.getActivity()

            if (dialog != null && !(activity != null && activity.isFinishing)) {
                dialog?.dismiss()
            }
        } catch (e: WindowManager.BadTokenException) {
            Log.i("hide dialog", "illegal")
        } catch (e: IllegalArgumentException) {
            Log.i("hide dialog", "illegal")
        }
    }

    fun setCancelable(cancelable: Boolean) {
        mCancelable = cancelable
    }

    override fun initDialog() {
        dialog = context.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCancelable(mCancelable)

        val display: Display? = dialog?.window?.windowManager?.defaultDisplay

        val size = Point()
        display?.getSize(size)
        width = size.x
        height = size.y

        binding = getViewBinding()

        binding?.root?.let { dialog?.setContentView(it) }

        initView()
    }

}