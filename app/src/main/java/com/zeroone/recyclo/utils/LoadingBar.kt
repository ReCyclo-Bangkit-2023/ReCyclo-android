package com.zeroone.recyclo.utils

import android.app.Activity
import android.app.AlertDialog
import com.zeroone.recyclo.R

class LoadingBar(val mActivity: Activity) {
    private lateinit var isDialog:AlertDialog
    fun startLoading(){
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.custom_loading_bar,null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}