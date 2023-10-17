package com.example.androiid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androiid.models.CourseData;
import com.example.androiid.models.Courses;
import com.example.androiid.service.ApiSetup;
import com.example.androiid.service.RetroInterface;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.androiid.databinding.ActivityStudentExploreBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class StudentExplore extends AppCompatActivity {

    private ActivityStudentExploreBinding binding;
    RetroInterface retroInterface;

    List<CourseData> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retroInterface = ApiSetup.getClient().create(RetroInterface.class);
        binding = ActivityStudentExploreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Recycler view stuffs
        SharedPreferences tokenPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String token = tokenPref.getString("token", "");
        getCourses(token);
        CoursesAdapter coursesAdapter = new CoursesAdapter(courses);
        RecyclerView recycler = binding.recyclerr;
        recycler.setOnClickListener(this::onClick);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(coursesAdapter);


        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitleEnabled(false);
        toolBarLayout.setTitle("Explore Courses");

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void onClick(View view) {

    }

    private void getCourses(String token){
        Call<Courses> caller = retroInterface.getCourse("Bearer " + token);
        caller.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {
                    StudentExplore.this.courses = response.body().data;
                    return;
                }
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}