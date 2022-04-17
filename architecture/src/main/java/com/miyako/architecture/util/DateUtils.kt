package com.miyako.architecture.util

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
 * @Description 日期工具类
 * @Author Miyako
 * @Date 2022-04-17-0017
 */
@RequiresApi(Build.VERSION_CODES.N)
class DateUtils {

    companion object {
        val FORMAT_MAP = mutableMapOf<String, SimpleDateFormat>()
        val SIMPLE_DATE_FORMAT = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")

        const val FORMAT_HMS = "YYYY-MM-dd HH:mm:ss"
        const val FORMAT_HM = "YYYY-MM-dd HH:mm"

        fun toDate(date: Long, pattern: String = FORMAT_HM): String {
            var format = FORMAT_MAP[pattern]
            if (format == null) {
                format = SimpleDateFormat(pattern)
                FORMAT_MAP[pattern] = format
            }
            return format.format(Date(date))
        }
    }
}