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
import com.example.studentrecordsystem.adapter.StudentAdapter;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Student;

import java.util.List;

public class ManageStudentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private DatabaseHelper dbHelper;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student_details);
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
            actionBar.setTitle("Manage Student Details"); // Set the title for the toolbar
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
            studentList = dbHelper.getAllStudent(userId);
            studentAdapter = new StudentAdapter(this, studentList, dbHelper);
            recyclerView.setAdapter(studentAdapter);

        } else {
            Intent i = new Intent(ManageStudentDetailsActivity.this, LoginActivity.class);
            startActivity(i);
            // Handle the case where the user ID is not found, e.g., show an error message or redirect to login
        }
    }

    private void getInitialization() {
        recyclerView = findViewById(R.id.recyclerViewStudent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPref.getInt("user_id", -1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshStudentList();
    }

    private void refreshStudentList() {
        int userId = getUserIdFromPreferences();
        if (userId != -1 && dbHelper != null) {
            List<Student> updatedList = dbHelper.getAllStudent(userId);
            if (studentAdapter != null) {
                studentAdapter.updateData(updatedList);
            }
        }
    }

    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ManageStudentDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

}