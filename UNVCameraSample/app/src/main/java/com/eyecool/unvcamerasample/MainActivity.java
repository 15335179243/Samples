package com.eyecool.unvcamerasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.eyecool.util.SystemUtil;
import com.eyecool.util.UnvUtil;
import com.sdk.CPlayView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String IP_ADDRESS_1 = "192.168.60.94";
    private static final String IP_ADDRESS_2 = "192.168.60.95";
    private static final String USER_NAME_1 = "admin";
    private static final String USER_NAME_2 = "admin";
    private static final String PWD_1 = "admin";
    private static final String PWD_2 = "admin";
    private CPlayView playView1;
    private CPlayView playView2;
    private UnvUtil unvUtil;
    private ProgressBar pbLoad1;
    private ProgressBar pbLoad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = SystemUtil.getOrientation(this);
        if (orientation == 1) {
            setContentView(R.layout.activity_main);
        } else if (orientation == 2) {
            setContentView(R.layout.activity_main_landscape);
        }

        pbLoad1 = findViewById(R.id.pb_load_1);
        pbLoad2 = findViewById(R.id.pb_load_2);
        playView1 = findViewById(R.id.player1);
        playView2 = findViewById(R.id.player2);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //todo 登录和播放都是耗时操作，但是可以在主线程执行，只不过会有几秒的白屏。但是如果在子线程执行则是黑屏，
        //todo 设置的背景色和进度条不显示。
        if (pbLoad1.getVisibility() == View.VISIBLE && pbLoad2.getVisibility() == View.VISIBLE) {
            unvUtil = UnvUtil.getInstance();
            int loginIDA = unvUtil.login(IP_ADDRESS_1, USER_NAME_1, PWD_1);
            int loginIDB = unvUtil.login(IP_ADDRESS_2, USER_NAME_2, PWD_2);

            unvUtil.play(loginIDA, playView1, 1, 1);
            unvUtil.play(loginIDB, playView2, 2, 2);
            pbLoad1.setVisibility(View.INVISIBLE);
            pbLoad2.setVisibility(View.INVISIBLE);
        }

    }

}
