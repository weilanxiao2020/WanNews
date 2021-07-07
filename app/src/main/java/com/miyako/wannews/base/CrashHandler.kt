package com.miyako.wannews.base

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process.killProcess
import android.os.Process.myPid
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

public class CrashHandler : Thread.UncaughtExceptionHandler {

    private val TAG = "CrashHandler"
    private val DEBUG = true

    //文件路径
    private val LOG_PATH: String = MyApplication.instance.getExternalFilesDir("log")?.absolutePath ?: ""
    private val CRASH_LOG_PATH: String = MyApplication.instance.getExternalFilesDir("crash")?.absolutePath ?: ""
    private val FILE_NAME = "crash"
    private val FILE_NAME_SUFEIX = ".trace"
    private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler? = null
    private lateinit var mContext: Context

    companion object {
        val obj = CrashHandler()
    }
    private fun CrashHandler() {}

    fun getInstance(): CrashHandler? {
        return obj
    }

    public fun init(context: Context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        mContext = context.getApplicationContext()
//        isDebug = Utils.isApkDebugable(BaseApplication.mApp)
//        getAllLog();
    }

    override fun uncaughtException(thread: Thread?, ex: Throwable) {
        try {
            //将文件写入sd卡
            writeToSDcard(ex)
            //写入后在这里可以进行上传操作
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        ex.printStackTrace()
        //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler!!.uncaughtException(thread, ex)
        } else {
            killProcess(myPid())
        }
    }

    //将异常写入文件
    private fun writeToSDcard(ex: Throwable) {
        //如果没有SD卡，直接返回
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            return
        }
        val appSpecificExternalDir = File(CRASH_LOG_PATH)
        if (!appSpecificExternalDir.exists()) {
            val isSuccess: Boolean = appSpecificExternalDir.mkdirs()
            Log.e(TAG, "isSuccess=$isSuccess")
        }
        val currenttime = System.currentTimeMillis()
        val time: String = SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(Date(currenttime))
        val exfile = File(appSpecificExternalDir.absolutePath + File.separator.toString() + FILE_NAME + time + FILE_NAME_SUFEIX)
        val pw = PrintWriter(BufferedWriter(FileWriter(exfile)))
        Log.e(TAG, "" + exfile.absolutePath)
        pw.println(time)
        val pm: PackageManager = mContext.packageManager
        val pi: PackageInfo = pm.getPackageInfo(mContext.packageName, PackageManager.GET_ACTIVITIES)
        //当前版本号
        pw.println("App Version: ${pi.versionName}_${pi.versionCode}")
        //当前系统
        pw.println("OS version: ${Build.VERSION.RELEASE}_${Build.VERSION.SDK_INT}")
        //制造商
        pw.println("Vendor: ${Build.MANUFACTURER}")
        //手机型号
        pw.println("Model: ${Build.MODEL}")
        //CPU架构
        pw.println("CPU ABI: ${Build.CPU_ABI}")
        ex.printStackTrace(pw)
        pw.close()
    }

    //文件路径
    //    private static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "tenda" + File.separator + "log";
    private var mLogThread: Thread? = null
    private var isDebug = false

    fun getAllLog() {
        if (!isDebug) {
            return
        }
        if (mLogThread == null || !mLogThread!!.isAlive) {
            //如果没有SD卡，直接返回
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return
            }
            val filedir = File(LOG_PATH)
            if (!filedir.exists()) {
                val isSuccess: Boolean = filedir.mkdirs()
                Log.e(TAG, "isSuccess=$isSuccess")
            }
            mLogThread = Thread(Runnable {
                var os: FileOutputStream = openOutputStream()
                try {
                    val p = Runtime.getRuntime().exec("logcat")
                    val `is`: InputStream = p.inputStream
                    var len = 0
                    var fileLenth = 0
                    val buf = ByteArray(1024)
                    while (-1 != `is`.read(buf).also({ len = it })) {
                        os?.write(buf, 0, len)
                        os?.flush()
                        fileLenth += len
                        //如果文件超过了30M则新建文件
                        if (fileLenth > 1024 * 1024 * 30) {
                            fileLenth = 0
                            closeOutputStream(os)
                            os = openOutputStream()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    closeOutputStream(os)
                }
            })
            mLogThread!!.start()
        }
    }

    private fun closeOutputStream(os: FileOutputStream) {
        var os: FileOutputStream = os
        if (null != os) {
            try {
                os.close()
            } catch (e: IOException) {
                // Do nothing
            }
        }
    }

    private fun openOutputStream(): FileOutputStream {
        var os: FileOutputStream
        val currenttime = System.currentTimeMillis()
        val time: String = SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(Date(currenttime))
        val logFilePath = LOG_PATH + File.separator.toString() + "log_" + time + ".log"
        os = FileOutputStream(logFilePath)
        try {
            writeAppInfoInFile(os)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return os
    }

    /**
     * log文件中写入app版本等信息
     *
     * @param os
     * @throws Exception
     */
    private fun writeAppInfoInFile(os: FileOutputStream) {
        val pm: PackageManager = mContext.packageManager
        val pi: PackageInfo = pm.getPackageInfo(mContext.packageName, PackageManager.GET_ACTIVITIES)
        val newLine = System.getProperty("line.separator")
        //当前版本号
        os.write(("App Version: ${pi.versionName}_${pi.versionCode}$newLine").toByteArray())
        //当前系统
        os.write(("OS version: ${Build.VERSION.RELEASE}_${Build.VERSION.SDK_INT}$newLine").toByteArray())
        //制造商
        os.write(("Vendor: ${Build.MANUFACTURER}$newLine").toByteArray())
        //手机型号
        os.write(("Model: ${Build.MODEL}$newLine").toByteArray())
        //CPU架构
        os.write(("CPU ABI: ${Build.CPU_ABI}$newLine").toByteArray())
        os.flush()
    }
}