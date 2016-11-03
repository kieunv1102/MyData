package com.entertaiment.photo.christmassticker.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.entertaiment.photo.christmassticker.R;

public class SplashActivity extends Activity {
    private long startTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startTime = System.currentTimeMillis();
        onSucessLoading();
    }
    private void onSucessLoading() {
        final long time = (System.currentTimeMillis() >= startTime + 2000) ? 0 : (2000 + startTime - System.currentTimeMillis());
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Intent it = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(it);
                        SplashActivity.this.finish();
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

}
