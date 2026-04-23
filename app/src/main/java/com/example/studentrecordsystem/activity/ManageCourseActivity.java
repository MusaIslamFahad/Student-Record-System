package com.example.studentrecordsystem.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.studentrecordsystem.MainActivity;
import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.adapter.CourseAdapter;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Course;

import java.util.List;

public class ManageCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private DatabaseHelper dbHelper;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);
        getInitialization();
        setUp();
        handleBackPress();
        toolbar();
    }
    private void toolbar() {
        // Find the toolbar and set it as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Manage Course"); // Set the title for the toolbar
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button action here
        onBackPressed();
        return true;
    }

    private void setUp() {
        dbHelper = new DatabaseHelper(this);
        int userId = getUserIdFromPreferences();
        if (userId != -1) {
            courseList = dbHelper.getAllCourses(userId);
            courseAdapter = new CourseAdapter(courseList, dbHelper);
            recyclerView.setAdapter(courseAdapter);
        } else {
            Intent i = new Intent(ManageCourseActivity.this, LoginActivity.class);
            startActivity(i);
            // Handle the case where the user ID is not found, e.g., show an error message or redirect to login
        }
    }

    private void getInitialization() {
        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPref.getInt("user_id", -1);
    }

    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ManageCourseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

}