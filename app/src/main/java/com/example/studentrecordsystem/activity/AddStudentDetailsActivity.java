package com.example.studentrecordsystem.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentrecordsystem.MainActivity;
import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Course;
import com.example.studentrecordsystem.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AddStudentDetailsActivity extends AppCompatActivity {

    private EditText editTextStudentName, editTextSession, editTextGuardianName, editTextNationality, editTextMobile, editTextEmail, editTextAddress,
            editTextCity, editTextState, editTextCountry, edtHscBoard, edtHscCGPA, edtSscBoard, edtSscCGPA;;
    private Spinner spinnerCourse, spinnerGender, spinnerPhysicaly;
    private Button buttonStudentSubmit;
    private DatabaseHelper dbHelper;

    private int userId;
    private List<Course> courseList;
    private ArrayAdapter<String> courseAdapter;
    private List<String> courseNames = new ArrayList<>();
    private String[] gender = {"Select Gender", "Male", "Female", "Others"};
    private String[] physically = {"Physically Challenged", "Yes", "No"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_details);
        getInitialization();
        setUp();
        handleBackPress();
        toolbar();
    }


    private void toolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Add Student Details");
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getInitialization() {
        editTextStudentName = findViewById(R.id.editTextFullName);
        editTextSession = findViewById(R.id.editTextSession);
        editTextGuardianName = findViewById(R.id.editTextGuardianName);
        editTextNationality = findViewById(R.id.editTextNationality);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextCity = findViewById(R.id.editTextCity);
        editTextState = findViewById(R.id.editTextState);
        editTextCountry = findViewById(R.id.editTextCountry);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerPhysicaly = findViewById(R.id.spinnerPhysically);
        edtHscBoard = findViewById(R.id.edtHscBoard);
        edtHscCGPA = findViewById(R.id.edtHscCGPA);
        edtSscBoard = findViewById(R.id.edtSscBoard);
        edtSscCGPA = findViewById(R.id.edtSscCGPA);
        buttonStudentSubmit = findViewById(R.id.buttonStudentSubmit);
        dbHelper = new DatabaseHelper(this);

        // Assuming userId is passed via Intent
        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Invalid user. Please login again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load members from database
        courseList = dbHelper.getAllCourses(userId);
        for (Course course : courseList) {
            courseNames.add(course.getSname());
        }

        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNames);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(courseAdapter);

        // Set up gender spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Set up physically spinner
        ArrayAdapter<String> adapterPhysically = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, physically);
        adapterPhysically.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhysicaly.setAdapter(adapterPhysically);
    }

    private void setUp() {
        buttonStudentSubmit.setOnClickListener(v -> {
            String fullNameStr = editTextStudentName.getText().toString().trim();
            String selectedCourse = (String) spinnerCourse.getSelectedItem();
            String selectedGender = (String) spinnerGender.getSelectedItem();
            String selectedPhysically = (String) spinnerPhysicaly.getSelectedItem();

            String session = editTextSession.getText().toString().trim();
            String guardian = editTextGuardianName.getText().toString().trim();
            String nationality = editTextNationality.getText().toString().trim();
            String mobile = editTextMobile.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String city = editTextCity.getText().toString().trim();
            String state = editTextState.getText().toString().trim();
            String country = editTextCountry.getText().toString().trim();

            String hscboard = edtHscBoard.getText().toString().trim();
            String hsccgpa = edtHscCGPA.getText().toString().trim();
            String sscboard = edtSscBoard.getText().toString().trim();
            String ssccgpa = edtSscCGPA.getText().toString().trim();



            if (fullNameStr.isEmpty() || selectedGender == null || selectedPhysically == null|| selectedCourse == null
                    || session.isEmpty() || guardian.isEmpty() || nationality.isEmpty()
                    || mobile.isEmpty() || email.isEmpty() || address.isEmpty()
                    || city.isEmpty() || state.isEmpty() || country.isEmpty()
                    || hscboard.isEmpty() || hsccgpa.isEmpty() || sscboard.isEmpty() || ssccgpa.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                boolean inserted = dbHelper.addStudent(selectedCourse, fullNameStr, selectedGender, selectedPhysically,
                        session, guardian, nationality, mobile, email, address, city, state, country, hscboard, hsccgpa, sscboard, ssccgpa, userId);

                if (inserted) {
                    Toast.makeText(this, "Student record saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ManageStudentDetailsActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Failed to save Student record", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number input", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(AddStudentDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}