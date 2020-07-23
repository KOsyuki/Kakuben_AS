package com.example.kakuben

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TODOlistActivity : AppCompatActivity(), View.OnClickListener, OnTouchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)
        findViewById<View>(R.id.back).setOnClickListener(this)
        immersiveMode()

        // 作成画面からデータを受け取り表示する
        val intent = intent
        val str = intent.getStringExtra("main_text")
        val str2 = intent.getStringExtra("main2_text")
        val tx = findViewById<Button>(R.id.task)
        tx.text = "$str  $str2"
        target = findViewById<View>(R.id.task) as Button
        target!!.setOnTouchListener(this)
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
    private var target: Button? = null
    private var targetLocalX = 0
    private var targetLocalY = 0
    private var screenX = 0
    private var screenY = 0
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                targetLocalX = target!!.left
                targetLocalY = target!!.top
                screenX = x
                screenY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = screenX - x
                val diffY = screenY - y
                targetLocalX -= diffX
                targetLocalY -= diffY
                target!!.layout(targetLocalX,
                        targetLocalY,
                        targetLocalX + target!!.width,
                        targetLocalY + target!!.height)
                screenX = x
                screenY = y
            }
        }
        return true
    }
}