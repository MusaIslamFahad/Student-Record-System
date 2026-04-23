package com.example.studentrecordsystem;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrecordsystem.activity.AddCourseActivity;
import com.example.studentrecordsystem.activity.AddStudentDetailsActivity;
import com.example.studentrecordsystem.activity.LoginActivity;
import com.example.studentrecordsystem.activity.ManageCourseActivity;
import com.example.studentrecordsystem.activity.ManageStudentDetailsActivity;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DatabaseHelper dbHelper;
    private int userId;

    private TextView totalCourseTextView, totalStudentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        userId = LoginActivity.getUserIdFromPreferences(this);
        if (userId == -1) {
            redirectToLogin();
            return;
        }

        setupToolbarAndDrawer();
        initViews();
        setupNavigationListener();
        setupCardViewListeners();
        displayTotals();
        setupBackPressedHandler();
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setupToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.navigation_view);

        androidx.appcompat.app.ActionBarDrawerToggle toggle = new androidx.appcompat.app.ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViews() {
        totalCourseTextView = findViewById(R.id.text_total_course);
        totalStudentTextView = findViewById(R.id.text_total_students);
    }

    private void setupNavigationListener() {
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            handleNavigationItem(item);
            return true;
        });
    }


    private void handleNavigationItem(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_add_course) {
            startActivity(new Intent(this, AddCourseActivity.class));
        } else if (id == R.id.nav_show_course) {
            startActivityWithUserId(ManageCourseActivity.class);
        } else if (id == R.id.nav_add_student) {
            startActivityWithUserId(AddStudentDetailsActivity.class);
        } else if (id == R.id.nav_manage_student) {
            startActivityWithUserId(ManageStudentDetailsActivity.class);
        } else if (id == R.id.nav_change_password) {
            changePassword();
        }else if (id == R.id.nav_logout) {
            logout();
        }
    }

    private void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Reset Password");

        // Create a layout for the dialog
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText usernameInput = new EditText(MainActivity.this);
        usernameInput.setHint("Enter Username");
        layout.addView(usernameInput);

        final EditText newPasswordInput = new EditText(MainActivity.this);
        newPasswordInput.setHint("Enter New Password");
        newPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(newPasswordInput);

        builder.setView(layout);

        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = usernameInput.getText().toString().trim();
                String newPassword = newPasswordInput.getText().toString().trim();

                if (username.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean updated = dbHelper.updatePassword(username, newPassword);
                    if (updated) {
                        Toast.makeText(MainActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void startActivityWithUserId(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("userId", userId);
        Log.d("MainActivity", "Starting " + cls.getSimpleName() + " with userId: " + userId);
        startActivity(intent);
    }

    private void setupCardViewListeners() {
        setupCardClickListener(R.id.item1, ManageCourseActivity.class);
        setupCardClickListener(R.id.item2, ManageStudentDetailsActivity.class);
    }

    private void setupCardClickListener(int cardViewId, Class<?> targetActivity) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> startActivityWithUserId(targetActivity));
    }

    private void displayTotals() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        totalCourseTextView.setText("Total No Courses: " + dbHelper.getTotalCourses());
        totalStudentTextView.setText("Total No Students: " + dbHelper.getTotalStudents());
    }
    private void logout() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit().remove("user_id").apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    showExitConfirmation();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void showExitConfirmation() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, id) -> MainActivity.super.onBackPressed())
                .setNegativeButton("No", null)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }
}