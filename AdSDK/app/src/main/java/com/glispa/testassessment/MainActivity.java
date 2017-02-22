package com.glispa.testassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.glispa.testassessment.server.Server;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.getInstance().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        setContentView(R.layout.activity_main);
    }

    public void openNativeAds(View view) {
        startActivity(new Intent(this, NativeActivity.class));
    }

    public void openImageAds(View view) {
        startActivity(new Intent(this, ImageFeedActivity.class));
    }

    @Override
    protected void onDestroy() {
        try {
            Server.getInstance().stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}