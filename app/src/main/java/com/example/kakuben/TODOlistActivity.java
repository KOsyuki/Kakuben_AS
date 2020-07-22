package com.example.kakuben;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TODOlistActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        findViewById(R.id.back).setOnClickListener(this);
        immersiveMode();

        // 作成画面からデータを受け取り表示する
        Intent intent = getIntent();
        String str = intent.getStringExtra("main_text");
        Button tx = findViewById(R.id.task);
        tx.setText(str);

        target = (Button)findViewById(R.id.task);
        target.setOnTouchListener(this);

    }

    //画面遷移処理
    public void onClick(View view){
        Intent intent = new Intent(this, SakuseiActivity.class);  //インテントの作成
        startActivity(intent);                                 //画面遷移
    }

    //没入モード
    private void immersiveMode(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            Log.d("debug","The system bars are visible");
                        } else {
                            Log.d("debug","The system bars are NOT visible");
                        }
                    }
                });
    }

    //ドラッグアンドドロップ処理
    private Button target;

    private int targetLocalX;
    private int targetLocalY;

    private int screenX;
    private int screenY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int)event.getRawX();
        int y = (int)event.getRawY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                targetLocalX = target.getLeft();
                targetLocalY = target.getTop();

                screenX = x;
                screenY = y;

                break;

            case MotionEvent.ACTION_MOVE:

                int diffX = screenX - x;
                int diffY = screenY - y;

                targetLocalX -= diffX;
                targetLocalY -= diffY;

                target.layout(targetLocalX,
                        targetLocalY,
                        targetLocalX + target.getWidth(),
                        targetLocalY + target.getHeight());

                screenX = x;
                screenY = y;

                break;
        }
        return true;
    }
}
