package com.example.web_picker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runWebPicker("https://www.google.pl");
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void runWebPicker(String stringUrl) {
        WebView browser = findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setAllowContentAccess(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.loadUrl(stringUrl);

        pauseWebPicker();
    }

    public void pauseWebPicker() {
        int pauseTime = 3204000 + (i * 3);
        if (i == 6) {
            pauseTime = 576000 - (i * 4);
        } else if (i == 10) {
            i = 0;
        }

        i++;

        ScrollView sv = (ScrollView) findViewById(R.id.scr);
        sv.scrollTo(0, sv.getBottom());

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(getApplicationContext(), "Switch to: " + i + " page.", Toast.LENGTH_SHORT).show();
            // Check if app is in background
            boolean b = ProcessLifecycleOwner.get().getLifecycle().getCurrentState() == Lifecycle.State.CREATED;
            // Check if app is in foreground
            boolean c = ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
            if (b) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            } else if (c) {
                Toast.makeText(getApplicationContext(), "App in Front", Toast.LENGTH_SHORT).show();
            }

            switch (i) {
                case 1:
                    runWebPicker("https://www.onet.pl/");
                    break;
                case 2:
                    runWebPicker("https://www.interia.pl/");
                    break;
                case 3:
                    runWebPicker("https://www.dziennik.pl/");
                    break;
                case 4:
                    runWebPicker("https://www.wp.pl/");
                    break;
                case 5:
                    runWebPicker("https://www.itbiznes.pl/");
                    break;
                case 6:
                    runWebPicker("https://music.youtube.com/watch?v=5K4rqCwpLyA&list=RDAMVMectgjkpq6As");
                    break;
                case 7:
                    runWebPicker("https://youtube.com/");
                    break;
                case 8:
                    runWebPicker("https://www.demotywatory.pl/");
                    break;
                case 9:
                    runWebPicker("https://www.kwejk.pl/");
                    break;
                case 10:
                    runWebPicker("https://www.wiocha.pl/");
                    break;
            }
        }, pauseTime);
    }
}