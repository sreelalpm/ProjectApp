package com.iconcept.projectapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.iconcept.projectapp.controller.SessionManager;

public class SplashscreenActivity extends AppCompatActivity {

    Intent intent;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        // Session Manager
        session = new SessionManager(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                proceedNextStep();
            }
        }, 1500);

    }

    public void proceedNextStep(){

        if (session.isLoggedIn()) {
            intent = new Intent(SplashscreenActivity.this, ProjectListActivity.class);
        } else {
            intent = new Intent(SplashscreenActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

}
