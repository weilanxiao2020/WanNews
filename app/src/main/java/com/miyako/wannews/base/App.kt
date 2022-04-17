package com.miyako.wannews.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.miyako.architecture.base.BaseApplication
import com.miyako.architecture.util.LogUtils
import com.miyako.architecture.util.Utils

class App : BaseApplication() {

    companion object {
        const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        LogUtils.level = LogUtils.LogLevel.VERBOSE
        CrashHandler().init(this)
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())
    }

    private class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityPaused")
        }

        override fun onActivityStarted(activity: Activity) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityStarted")
        }

        override fun onActivityDestroyed(activity: Activity) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityDestroyed")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivitySaveInstanceState")
        }

        override fun onActivityStopped(activity: Activity) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityStopped")
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityCreated")
        }

        override fun onActivityResumed(activity: Activity) {
            LogUtils.i(TAG, "${activity.toString()} ==> onActivityResumed")
        }

    }
}