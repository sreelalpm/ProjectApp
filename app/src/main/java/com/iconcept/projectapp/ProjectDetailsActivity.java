package com.iconcept.projectapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iconcept.projectapp.controller.SessionManager;

public class ProjectDetailsActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    TextView txtDescription, txtAuthor, txtStatus, txtCreated, txtUpdated;
    ImageButton btnSignout;
    Intent intent;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtCreated = (TextView) findViewById(R.id.txtCreated);
        txtUpdated = (TextView) findViewById(R.id.txtUpdated);

        collapsingToolbar.setTitle(getIntent().getExtras().getString("title"));

        txtDescription.setText(Html.fromHtml(getIntent().getExtras().getString("description")));
        txtAuthor.setText("Author : " + getIntent().getExtras().getString("author"));
        txtStatus.setText("Status : " + getIntent().getExtras().getString("status"));
        txtCreated.setText("Created at : " + getIntent().getExtras().getString("created"));
        txtUpdated.setText("Updated at : " + getIntent().getExtras().getString("updated"));

        btnSignout = (ImageButton) findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                intent = new Intent(ProjectDetailsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
