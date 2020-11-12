package com.manueh.wikigi.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.manueh.wikigi.R;

import java.util.Timer;
import java.util.TimerTask;

public class Logotype extends AppCompatActivity {
    private String TAG="wikigi/Logotype";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logotype);
        getSupportActionBar().hide();
        Log.d(TAG,"Método que quita el logo después de 3 segundos");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}