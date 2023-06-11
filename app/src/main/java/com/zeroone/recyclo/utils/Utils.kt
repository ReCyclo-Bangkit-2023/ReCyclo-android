package com.zeroone.recyclo.utils

import java.text.NumberFormat
import java.util.Locale

object Utils {
    fun formatrupiah(number: Double): String {
        val localeID = Locale("IND", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val formatRupiah = numberFormat.format(number)
        val split = formatRupiah.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val length = split[0].length
        return split[0].substring(0, 2) + ". " + split[0].substring(2, length)
    }
}