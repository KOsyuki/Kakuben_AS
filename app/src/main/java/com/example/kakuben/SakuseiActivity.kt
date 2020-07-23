package com.example.kakuben

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SakuseiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sakusei)
        immersiveMode()

        //値受け渡し処理
        findViewById<View>(R.id.ok).setOnClickListener {
            val et = findViewById<EditText>(R.id.name)
            val et2 = findViewById<EditText>(R.id.number)

            // インテントの準備
            val intent = Intent(this@SakuseiActivity, TODOlistActivity::class.java)

            // サブ画面に渡す値
            intent.putExtra("main_text", et.text.toString())
            intent.putExtra("main2_text", et2.text.toString())

            // サブ画面を表示する
            startActivity(intent)
        }
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
}