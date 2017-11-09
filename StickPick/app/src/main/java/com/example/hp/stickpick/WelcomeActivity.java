package com.example.hp.stickpick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread t1 = new Thread() {
            public void run() {

                try {
                    sleep(3 * 1000);
                    Intent i = new Intent(WelcomeActivity.this, SplashActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                }
            }
        };
        t1.start();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
