package com.example.bookplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookplaza.user.UserOperation;

/**
 * Activity for displaying User Register page
 *
 * Users can register to new accounts
 * Check the validity of user input (Check if input is empty, Check whether the password entered twice is the same)
 *
 * @author Xiangji Kong
 */
public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Register button
        Button button = (Button) findViewById(R.id.buttonReg);
        button.setOnClickListener(buttonListener);
    }

    // Register button listener
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // get userName and password from input
            EditText userName = (EditText) findViewById(R.id.userNameTextReg);
            EditText password = (EditText) findViewById(R.id.passwordTextReg);
            EditText confirmPassword = (EditText) findViewById(R.id.confirmPwdTextReg);

            String inputUserName = userName.getText().toString().trim();
            String inputPassword = password.getText().toString().trim();
            String inputConfirmPassword = confirmPassword.getText().toString().trim();


            // confirm user input is valid
            if (inputUserName.equals("") || inputPassword.equals("") || !inputPassword.equals(inputConfirmPassword)) {
                Toast.makeText(UserRegister.this, "Invalid input or the two input passwords are different", Toast.LENGTH_LONG).show();
                return;
            }


            // if the input user name is valid
            if (UserOperation.getInstance().register(inputUserName, inputPassword)) {
                Toast.makeText(UserRegister.this, "Register success!", Toast.LENGTH_LONG).show();

                // Clear the EditText
                userName.setText("");
                password.setText("");
                confirmPassword.setText("");

                // open the login page
                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                startActivity(intent);

            } else {
                // Display a toast message for an invalid user name
                Toast.makeText(UserRegister.this, "User name is occupied", Toast.LENGTH_LONG).show();
            }


        }
    };
}