package com.miyako.architecture.util

import android.util.Log

/**
 * @Description 日志工具类
 * @Author Miyako
 * @Date 2022-04-14-0014
 */
object LogUtils {

    enum class LogLevel {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    var level: LogLevel = LogLevel.VERBOSE
        set(value) {
            Log.e("LogUtils", "set level = $value")
            field = value
        }

    fun v(tag:String, msg:String) {
        if (level <= LogLevel.VERBOSE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag:String, msg:String) {
        if (level <= LogLevel.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag:String, msg:String) {
        if (level <= LogLevel.INFO) {
            Log.i(tag, msg)
        }
    }

    fun w(tag:String, msg:String) {
        if (level <= LogLevel.WARN) {
            Log.w(tag, msg)
        }
    }

    fun e(tag:String, msg:String) {
        if (level <= LogLevel.ERROR) {
            Log.e(tag, msg)
        }
    }

}