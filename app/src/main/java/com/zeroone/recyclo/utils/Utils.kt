package com.zeroone.recyclo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.widget.ImageView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.buffer
import okio.sink
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

object Utils {
    fun formatrupiah(number: Double?): String {
        val localeID = Locale("IND", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val formatRupiah = numberFormat.format(number)
        val split = formatRupiah.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val length = split[0].length
        return split[0].substring(0, 2) + ". " + split[0].substring(2, length)
    }

    fun CurrencyToNumber(curr: String): Long {
        var s = ""
        val splited = curr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        s = splited[1]
        return if (curr.contains(".")) {
            val splitedTwc = s.split("[.]".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            var result = ""
            for (split in splitedTwc) {
                result += split
            }
            result.toLong()
        } else {
            s.toLong()
        }
    }

    fun convertImageViewToFile(context: Context, imageView: ImageView): File? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val file = createImageFile(context)
            try {
                val outputStream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.flush()
                outputStream.close()
                return file
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun createImageFile(context: Context): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "IMG_${UUID.randomUUID()}",
            ".jpg",
            storageDir
        )
    }
}