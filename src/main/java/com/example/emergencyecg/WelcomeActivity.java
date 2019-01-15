package com.example.emergencyecg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.qiniu.droid.rtc.demo.utils.QNAppServer;
import com.qiniu.droid.rtc.demo.utils.ToastUtils;

public class WelcomeActivity extends AppCompatActivity {

    private Handler mWelcomeHandler;

    public String roomName="mmmmm";

    public String mUserName = QNAppServer.ADMIN_USER;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String token = QNAppServer.getInstance().requestRoomToken(WelcomeActivity.this, mUserName, roomName);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (token == null) {
                            ToastUtils.s(WelcomeActivity.this, getString(R.string.null_room_token_toast));
                            return;
                        }
                        Intent intent = new Intent(WelcomeActivity.this, RoomActivity.class);
                        intent.putExtra(RoomActivity.EXTRA_ROOM_ID, roomName.trim());
                        intent.putExtra(RoomActivity.EXTRA_ROOM_TOKEN, token);
                        intent.putExtra(RoomActivity.EXTRA_USER_ID, mUserName);


                        startActivity(intent);
                    }
                });
            }
        }).start();


//        final String token = QNAppServer.getInstance().requestRoomToken(WelcomeActivity.this, "admin", "mmmmm");

//        mWelcomeHandler = new Handler();
//        mWelcomeHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);



    }

    @Override
    public void onBackPressed() {
        mWelcomeHandler.removeCallbacksAndMessages(null);
        finish();
    }
}
