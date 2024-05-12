package com.example.bookplaza;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.HashMapReader;
import com.example.bookplaza.io.HashMapWriter;
import com.example.bookplaza.user.UserOperation;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Activity for displaying User Login page
 * Users can login to their accounts
 * Users can register to new accounts
 *
 * Default users can login directly
 * comp2100@anu.edu.au,comp2100
 * comp6442@anu.edu.au,comp6442
 *
 * @author Xiangji Kong
 */
public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // exit the app
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
            System.exit(0);
        }

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // login button
        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(loginButtonListener);

        // register button
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(registerButtonListener);

        //Initial data check
        UserOperation.setApplicationDir(getApplicationContext().getFilesDir());
        UserOperation userOperation= UserOperation.getInstance();
        try {
            userOperation.dataCheck(
                    getApplicationContext().getAssets().open("userData.csv"),
                    getApplicationContext().getAssets().open(Data.BOOK_CSV_FILE)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // login button listener
    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // get userName and password from input
            EditText userName = (EditText) findViewById(R.id.userNameText);
            EditText password = (EditText) findViewById(R.id.passwordText);

            String InputUserName = userName.getText().toString().trim();
            String InputPassword = password.getText().toString().trim();

            // Clear the EditText
            userName.setText("");
            password.setText("");

            // if the input user name and password is valid
            if (UserOperation.getInstance().login(InputUserName, InputPassword)) {

                // pass information and open the main page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
            else {
                // Display a toast message for an invalid user name or password
                Toast.makeText(UserLogin.this, "User name or Password is invalid", Toast.LENGTH_LONG).show();
            }

        }
    };

    // register button listener
    private View.OnClickListener registerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Start the UserRegister activity
            Intent intent = new Intent(UserLogin.this, UserRegister.class);
            startActivity(intent);
        }
    };
}