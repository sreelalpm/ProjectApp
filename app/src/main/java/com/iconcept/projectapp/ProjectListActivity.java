package com.iconcept.projectapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iconcept.projectapp.adapter.ProjectDetails;
import com.iconcept.projectapp.adapter.SimpleRecyclerAdapter;
import com.iconcept.projectapp.controller.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ProjectListActivity extends AppCompatActivity{

    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    CardView listItem;
    SimpleRecyclerAdapter simpleRecyclerAdapter;
    ImageButton btnSignout;
    Intent intent;

    SessionManager session;

    ArrayList<ProjectDetails> projectList;
    ProgressDialog progress;

    ProjectDetails project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_projectlist);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Projects");

        btnSignout = (ImageButton) findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                intent = new Intent(ProjectListActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        projectList = new ArrayList<ProjectDetails>();

        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        listItem = (CardView) findViewById(R.id.cardlist_item);
        getProjectList();

        if (simpleRecyclerAdapter == null) {
            simpleRecyclerAdapter = new SimpleRecyclerAdapter(projectList, getApplicationContext());
            recyclerView.setAdapter(simpleRecyclerAdapter);
        }

        simpleRecyclerAdapter.SetOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ProjectListActivity.this, ProjectDetailsActivity.class);
                intent.putExtra("title", projectList.get(position).getTitle()+"");
                intent.putExtra("description", projectList.get(position).getDescription()+"");
                intent.putExtra("author", projectList.get(position).getAuthor()+"");
                intent.putExtra("status", projectList.get(position).getStatus()+"");
                intent.putExtra("created", projectList.get(position).getCreated_time()+"");
                intent.putExtra("updated", projectList.get(position).getUpdated_time()+"");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getProjectList() {
        /*Json Request*/
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://co42.net/api/projects/" + session.getUserId();
        Log.e("Request => ", url);
        showLoader();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideLoader();
                        //Log.e("Response -> ", response.toString());
                        try {

                            if (response.length() > 0) {
                                JSONObject object = new JSONObject(String.valueOf(response));
                                JSONArray objJobs = object.getJSONArray("issues");

                                for (int i = 0; i < objJobs.length(); i++) {
                                    JSONObject newObj = objJobs.getJSONObject(i);
                                    project = new ProjectDetails();
                                    project.setProjectId(newObj.getInt("id"));
                                    project.setAuthor(newObj.getString("created_by"));
                                    project.setTitle(newObj.getString("title"));
                                    project.setDescription(newObj.getString("body"));
                                    project.setStatus(newObj.getString("status"));
                                    project.setPriority(newObj.getInt("priority"));
                                    project.setCreated_time(newObj.getString("created_at"));
                                    project.setUpdated_time(newObj.getString("updated_at"));

                                    projectList.add(project);
                                }

                                //Sorting list based on priority
                                Collections.sort(projectList, new Comparator<ProjectDetails>(){
                                    public int compare(ProjectDetails obj1, ProjectDetails obj2) {
                                        return Integer.valueOf(obj2.getPriority()).compareTo(obj1.getPriority());
                                    }
                                });

                                simpleRecyclerAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "No projects found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("exception", e + "");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideLoader();
                        Log.e("Volley -> ", error.toString());
                        Toast.makeText(getBaseContext(), "No internet connection found..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //add params <key,value>
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // add headers <key,value>
                String auth = session.getAuthString();
                headers.put("Authorization", auth);
                session.saveAuthString(auth);
                return headers;
            }

        };
        //add request to queue
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

    public void showLoader(){
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

    }

    public void hideLoader(){
        // To dismiss the dialog
        progress.dismiss();
    }
}
