package com.example.studentrecordsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Student;

import java.util.List;

public class EditStudentActivity extends AppCompatActivity {

    private EditText etCourse, etName, etGender, etPhysically, etSession, etGuardian, etNationality, etMobile,
            etEmail, etAddress, etCity, etState, etCountry, etHscBoard, etHscCgpa, etSscBoard, etSscCgpa;
    private Button btnUpdate;
    private DatabaseHelper dbHelper;
    private int studentId;
    private Student studentToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        // Initialize views
        etCourse = findViewById(R.id.etCourse);
        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        etPhysically = findViewById(R.id.etPhysically);
        etSession = findViewById(R.id.etSession);
        etGuardian = findViewById(R.id.etGuardian);
        etNationality = findViewById(R.id.etNationality);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etCountry = findViewById(R.id.etCountry);
        etHscBoard = findViewById(R.id.etHscBoard);
        etHscCgpa = findViewById(R.id.etHscCgpa);
        etSscBoard = findViewById(R.id.etSscBoard);
        etSscCgpa = findViewById(R.id.etSscCgpa);

        btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DatabaseHelper(this);

        // Get student ID from intent
        studentId = getIntent().getIntExtra("studentId", -1);
        Log.d("EditStudentActivity", "Received studentId: " + studentId);

        if (studentId == -1) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadStudentData(studentId);

        btnUpdate.setOnClickListener(v -> updateStudent());
    }

    private void loadStudentData(int id) {
        // Use getStudentById instead of looping
        studentToEdit = dbHelper.getStudentById(id);

        if (studentToEdit == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set data to EditTexts
        etCourse.setText(studentToEdit.getCourse());
        etName.setText(studentToEdit.getStudentname());
        etGender.setText(studentToEdit.getGender());
        etPhysically.setText(studentToEdit.getPhysically());
        etSession.setText(studentToEdit.getSession());
        etGuardian.setText(studentToEdit.getGuardian());
        etNationality.setText(studentToEdit.getNationality());
        etMobile.setText(studentToEdit.getMobile());
        etEmail.setText(studentToEdit.getEmail());
        etAddress.setText(studentToEdit.getAddress());
        etCity.setText(studentToEdit.getCity());
        etState.setText(studentToEdit.getState());
        etCountry.setText(studentToEdit.getCountry());
        etHscBoard.setText(studentToEdit.getHscboard());
        etHscCgpa.setText(studentToEdit.getHsccgpa());
        etSscBoard.setText(studentToEdit.getSscboard());
        etSscCgpa.setText(studentToEdit.getSsccgpa());
    }

    private void updateStudent() {
        String course = etCourse.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String physically = etPhysically.getText().toString().trim();
        String session = etSession.getText().toString().trim();
        String guardian = etGuardian.getText().toString().trim();
        String nationality = etNationality.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String hscboard = etHscBoard.getText().toString().trim();
        String hsccgpa = etHscCgpa.getText().toString().trim();
        String sscboard = etSscBoard.getText().toString().trim();
        String ssccgpa = etSscCgpa.getText().toString().trim();

        boolean success = dbHelper.updateStudent(studentId, course, name, gender, physically, session, guardian,
                nationality, mobile, email, address, city, state, country, hscboard, hsccgpa, sscboard, ssccgpa);

        if (success) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
        }
    }
}
