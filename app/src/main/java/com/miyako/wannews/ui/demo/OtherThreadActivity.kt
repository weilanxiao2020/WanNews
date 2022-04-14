package com.miyako.wannews.ui.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import com.miyako.util.LogUtils
import com.miyako.wannews.R
import kotlin.concurrent.thread

class OtherThreadActivity : AppCompatActivity() {

    val TAG = "OtherThreadActivity"

    var threadButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_thead)

        findViewById<Button>(R.id.btn_exact_size).setOnClickListener {
            val textView = findViewById<TextView>(R.id.tv_exact_text)
            thread {
                LogUtils.d(TAG, "固定尺寸更新，子线程")
                textView.text = "固定尺寸更新，子线程"
                // 不出错，开启硬件加速
            }
        }

        findViewById<Button>(R.id.btn_wrap_size).setOnClickListener {
            val textView = findViewById<TextView>(R.id.tv_wrap_text)
            // region 出错
            // thread {
            //     LogUtils.d(TAG, "不固定尺寸更新，子线程")
            //     textView.text = "固定尺寸更新，子线程"
            // }
            // endregion

            // region 不出错
            textView.text = "other"
            thread {
                LogUtils.d(TAG, "不固定尺寸更新，子线程")
                textView.text = "固定尺寸更新，子线程"
            }
            // endregion
        }

        findViewById<Button>(R.id.btn_after_request_layout).setOnClickListener {
            val textView = findViewById<TextView>(R.id.tv_after_request_layout_text)
            textView.requestLayout()
            thread {
                LogUtils.d(TAG, "requestLayout后，子线程")
                textView.text = "requestLayout后，子线程"
            }
        }

        findViewById<Button>(R.id.btn_other_view_root_impl).setOnClickListener(this::clickViewRootImpl)

        // thread {
        //     Looper.prepare()
        //     threadButton = Button(this)
        //     val layoutParams = WindowManager.LayoutParams()
        //     layoutParams.height = 200
        //     layoutParams.gravity = Gravity.BOTTOM
        //     windowManager.addView(threadButton, layoutParams)
        //     threadButton.setOnClickListener {
        //         LogUtils.d(TAG, "子线程使用自己的ViewRootImpl")
        //         threadButton.text = "子线程使用自己的ViewRootImpl"
        //     }
        //     Looper.loop()
        // }

        findViewById<Button>(R.id.btn_surface_view).setOnClickListener {

        }
        val surface = findViewById<SurfaceView>(R.id.surface_view)
        surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                thread {
                    if (!isDestroyed) {
                        LogUtils.d(TAG, "Surface，子线程")
                        Thread.sleep(2000)
                        val lockCanvas = holder.lockCanvas()
                        lockCanvas.drawColor(android.graphics.Color.RED)
                        holder.unlockCanvasAndPost(lockCanvas)
                    }
                }
            }

            override fun surfaceCreated(holder: SurfaceHolder) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })
    }


    fun clickViewRootImpl(view: View): Unit {
        threadButton?.performClick()
    }
}