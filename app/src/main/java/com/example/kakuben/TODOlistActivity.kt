package com.example.kakuben

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TODOlistActivity : AppCompatActivity(), View.OnClickListener, OnTouchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        findViewById<View>(R.id.plus).setOnClickListener(this)


        // 作成画面からデータを受け取り表示する
        val intent = intent
        val str = intent.getStringExtra("main_text")
        val str2 = intent.getStringExtra("main2_text")
        val tx = findViewById<Button>(R.id.task1)
        tx.text = "$str   $str2"

        target1 = findViewById<View>(R.id.task1) as Button
        target1!!.setOnTouchListener(this)


        //背景色クリック表示
        val b2 = findViewById<Button>(R.id.button2)
        b2.visibility = View.GONE
        val ao = findViewById<Button>(R.id.button22)
        ao.setOnClickListener{
            b2.visibility = View.VISIBLE
        }

        //タスク消去
        val t = findViewById<Button>(R.id.task)
        val t2 = findViewById<Button>(R.id.task2)
        val t3 = findViewById<Button>(R.id.task3)
        val t4 = findViewById<Button>(R.id.task4)
        val t5 = findViewById<Button>(R.id.task5)
        val t6 = findViewById<Button>(R.id.task6)
        t.setOnClickListener{
            t.visibility = View.GONE
        }
        t2.setOnClickListener{
            t2.visibility = View.GONE
        }
        t3.setOnClickListener{
            t3.visibility = View.GONE
        }
        t4.setOnClickListener{
            t4.visibility = View.GONE
        }
        t5.setOnClickListener{
            t5.visibility = View.GONE
        }
        t6.setOnClickListener{
            t6.visibility = View.GONE
        }

        //クリア表示
        val b6 = findViewById<Button>(R.id.button6)
        b6.visibility = View.GONE
        val b7 = findViewById<Button>(R.id.button7)
        b7.visibility = View.GONE
        val b8 = findViewById<Button>(R.id.button8)
        b8.visibility = View.GONE
        val b9 = findViewById<Button>(R.id.button9)
        b9.visibility = View.GONE
        val cl = findViewById<TextView>(R.id.clear)
        cl.visibility = View.GONE
        val cl2 = findViewById<TextView>(R.id.clear2)
        cl2.visibility = View.GONE
        val cl3 = findViewById<TextView>(R.id.clear3)
        cl3.visibility = View.GONE
        val cl4 = findViewById<TextView>(R.id.clear4)
        cl4.visibility = View.GONE
        val switch1 = findViewById<Switch>(R.id.switch1)
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            b7.visibility = View.VISIBLE
            cl3.visibility = View.VISIBLE
        }
        val switch2 = findViewById<Switch>(R.id.switch2)
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            b6.visibility = View.VISIBLE
            cl.visibility = View.VISIBLE
        }
        val switch4 = findViewById<Switch>(R.id.switch4)
        switch4.setOnCheckedChangeListener { buttonView, isChecked ->
            b8.visibility = View.VISIBLE
            cl4.visibility = View.VISIBLE
        }

        //後光への画面遷移
        val switch3 = findViewById<Switch>(R.id.switch3)
        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            val intent = Intent(application, GokouActivity::class.java)
            startActivity(intent)
        }
    }

    //TODO作成画面への画面遷移
    override fun onClick(view: View) {
        val intent = Intent(this, SakuseiActivity::class.java) //インテントの作成
        startActivity(intent) //画面遷移
    }

    //フルスクリーンモード
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
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