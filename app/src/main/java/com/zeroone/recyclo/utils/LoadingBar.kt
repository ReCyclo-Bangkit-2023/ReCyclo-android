package com.zeroone.recyclo.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat

import com.zeroone.recyclo.R

class LoadingBar(val mActivity: Activity) {
    private lateinit var isDialog:Dialog

    fun startLoading(){
         var cpCardView: CardView
         var progressBar: ProgressBar

        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.custom_loading_bar,null)

        isDialog = Dialog(mActivity)
        isDialog.window?.decorView?.rootView?.setBackgroundResource(R.color.transparent)
        isDialog.window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
            insets.consumeSystemWindowInsets()
        }



        isDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        isDialog.setContentView(dialogView)
        isDialog.setCancelable(false)
        isDialog.show()

    }


    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}
