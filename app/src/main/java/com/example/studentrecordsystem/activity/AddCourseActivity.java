package com.example.studentrecordsystem.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentrecordsystem.MainActivity;
import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.database.DatabaseHelper;

import java.util.Arrays;

public class AddCourseActivity extends AppCompatActivity {

    EditText edtFullName, edtShortName;
    Button btnAddCourse;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

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
            actionBar.setTitle("Add Course"); // Set the title for the toolbar
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button action here
        onBackPressed();
        return true;
    }

    private void getInitialization() {
        edtFullName = findViewById(R.id.edtCourseFullName);
        edtShortName = findViewById(R.id.edtCourseShortName);
        btnAddCourse = findViewById(R.id.btnAddCourse);
        dbHelper = new DatabaseHelper(this);
    }

    private void setUp() {


        btnAddCourse.setOnClickListener(v -> {
            String fname = edtFullName.getText().toString().trim();
            String sname = edtShortName.getText().toString().trim();

            if (fname.isEmpty() || sname.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = getUserIdFromPreferences();
            if (userId != -1) {
                boolean isAdded = dbHelper.addCourse(fname, sname, userId);
                if (isAdded) {
                    Toast.makeText(this, "course added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ManageCourseActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add course", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private int getUserIdFromPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPref.getInt("user_id", -1);
    }

    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}