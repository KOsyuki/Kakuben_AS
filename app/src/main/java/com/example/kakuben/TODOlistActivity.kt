package com.example.kakuben

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class TODOlistActivity : AppCompatActivity(), View.OnClickListener, OnTouchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        findViewById<View>(R.id.task1).setOnClickListener(this)
        immersiveMode()

        // 作成画面からデータを受け取り表示する
        val intent = intent
        val str = intent.getStringExtra("main_text")
        val str2 = intent.getStringExtra("main2_text")
        val tx = findViewById<Button>(R.id.task1)
        tx.text = "$str  $str2"

        target1 = findViewById<View>(R.id.task1) as Button
        target1!!.setOnTouchListener(this)
    }

    //画面遷移処理
    override fun onClick(view: View) {
        val intent = Intent(this, SakuseiActivity::class.java) //インテントの作成
        startActivity(intent) //画面遷移
    }

    //没入モード
    private fun immersiveMode() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        decorView.setOnSystemUiVisibilityChangeListener { visibility -> // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                Log.d("debug", "The system bars are visible")
            } else {
                Log.d("debug", "The system bars are NOT visible")
            }
        }
    }

    //ドラッグアンドドロップ処理
    private var target1: Button? = null
    private var targetLocalX1 = 0
    private var targetLocalY1 = 0
    private var screenX1 = 0
    private var screenY1 = 0
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x1 = event.rawX.toInt()
        val y1 = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                targetLocalX1 = target1!!.left
                targetLocalY1 = target1!!.top
                screenX1 = x1
                screenY1 = y1
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX1 = screenX1 - x1
                val diffY1 = screenY1 - y1
                targetLocalX1 -= diffX1
                targetLocalY1 -= diffY1
                target1!!.layout(targetLocalX1,
                        targetLocalY1,
                        targetLocalX1 + target1!!.width,
                        targetLocalY1 + target1!!.height)
                screenX1 = x1
                screenY1 = y1
            }
        }
        return false
    }
}