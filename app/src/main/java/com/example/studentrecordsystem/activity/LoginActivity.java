package com.example.studentrecordsystem.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.MainActivity;
import com.example.studentrecordsystem.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser, editTextPassword;
    private TextView textViewForgotPassword;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInitialization();
        setUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUp() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextUser.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (user.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.checkUser(user, password)) {
                        int userId = dbHelper.getUserId(user, password);
                        saveUserIdInPreferences(userId);
                        // Navigate to main activity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid phone or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Reset Password");

        // Create a layout for the dialog
        LinearLayout layout = new LinearLayout(LoginActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText usernameInput = new EditText(LoginActivity.this);
        usernameInput.setHint("Enter Username");
        layout.addView(usernameInput);

        final EditText newPasswordInput = new EditText(LoginActivity.this);
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
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean updated = dbHelper.updatePassword(username, newPassword);
                    if (updated) {
                        Toast.makeText(LoginActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    private void getInitialization() {
        editTextUser = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        dbHelper = new DatabaseHelper(this);
        handleBackPress();
    }

    private void saveUserIdInPreferences(int userId) {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    public static int getUserIdFromPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPref.getInt("user_id", -1);
    }

    private void handleBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}