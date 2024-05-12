package com.example.bookplaza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bookplaza.util.CheckBoxUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying search page
 *
 * Users can choose 1 of 3 featured groups to search
 * There is a tip to help users start searching
 * There will be a prompt when the user input is incorrect
 *
 * @author Xiangji Kong
 */
public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder builder; // Exit program dialog
    private List<CheckBox> radios = new ArrayList<>();

    // bookIndicesInTheCurrentGroup has value = null / DailyReading / HotFeed / Explore
    private String selectedBookGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init exit program dialog
        initDialog();

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // search button
        Button searchButton = (Button) findViewById(R.id.searchButtonMain);
        searchButton.setOnClickListener(searchButtonListener);

        // user center button
        Button userCterButton = (Button) findViewById(R.id.userCterBtn);
        userCterButton.setOnClickListener(userCterButtonListener);

        // group select buttons
        radios.add(findViewById(R.id.radio1));
        radios.add(findViewById(R.id.radio2));
        radios.add(findViewById(R.id.radio3));

        // Set click listeners for group select buttons
        for (CheckBox radio : radios) {
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeRadios((CheckBox) v);
                }
            });
        }
    }

    // search button listener
    private View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // get text from input
            EditText input = (EditText) findViewById(R.id.input);
            String userInput = input.getText().toString().trim();
            input.setText(""); // Clear the EditText

            // pass information and open the result page
            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
            intent.putExtra("input", userInput);

            // bookIndicesInTheCurrentGroup has value = null / DailyReading / HotFeed / Explore
            intent.putExtra("selectedBookGroup", selectedBookGroup);
            startActivity(intent);
        }
    };

    // group select buttons listener
    void changeRadios(CheckBox checkBox) {
        CheckBoxUtil.unCheck(radios);
        checkBox.setChecked(true);

        selectedBookGroup = CheckBoxUtil.getOne(radios);
    }

    // user center button listener
    private View.OnClickListener userCterButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // pass information and open the result page
            Intent intent = new Intent(getApplicationContext(), UserCenter.class);
            startActivity(intent);
        }
    };

    // Click back button to exit the program
    private void initDialog() {
        builder = new AlertDialog.Builder(this)
                .setMessage("Exit the program?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
//                    android.os.Process.killProcess(android.os.Process.myPid());
                    Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
    }

    // back button listener
    @Override
    public void onBackPressed() {
        builder.show();
    }
}
