package com.example.bookplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bookplaza.user.UserOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Activity for displaying user center, containing profile picture and search history
 *
 * Users can click on the profile picture to change it
 * Users can browse the search history and click on the history to search again
 * Users can logout safely
 *
 * @author Xiangji Kong
 */
public class UserCenter extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView userImage;

    private ArrayList<String> historyList= new ArrayList<>();
    private ArrayAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get user information from main page
        String userName = UserOperation.getInstance().getUsername();
        historyList.addAll(UserOperation.getInstance().getSearchHistory());

        // set user welcome
        TextView userWelcome = (TextView) findViewById(R.id.userWelcome);
        userWelcome.setText("Hi! " + userName);

        // home button
        Button userCterButton = (Button) findViewById(R.id.homeBtn);
        userCterButton.setOnClickListener(homeButtonListener);

        // logout button
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(logoutButtonListener);

        // user image
        userImage = findViewById(R.id.userImage);
        userImage.setOnClickListener(userImageListener);
        Uri imageUri = UserOperation.getInstance().getUerImageUri();
        if ( imageUri == null) {
            userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        }else{
            userImage.setImageURI(imageUri);
        }

        // add ListView to history list
        ListView urlListView = (ListView) findViewById(R.id.historyList);
        historyListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, historyList);
        urlListView.setAdapter(historyListAdapter);

        // Set an OnItemClickListener for the history list
        urlListView.setOnItemClickListener(historyListListener);

    }

    // OnItemClickListener for the history list
    private AdapterView.OnItemClickListener historyListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Handle item clicked
            String selectedHistory = historyList.get(i).toString();

            // pass information and open the result page
            Intent intent = new Intent(getApplicationContext(), SearchResults.class);
            intent.putExtra("input", selectedHistory);
            startActivity(intent);
        }
    };

    // user center button listener
    private View.OnClickListener homeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // go to home page
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    // logout button button listener
    private View.OnClickListener logoutButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(UserOperation.getInstance().logout()) {
                Toast.makeText(UserCenter.this, "Logout success!", Toast.LENGTH_LONG).show();

                // go to login page
                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                startActivity(intent);
            }
        }
    };

    // user image listener
    private View.OnClickListener userImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openImagePicker();
        }
    };

    // Open the image picker or gallery intent
    private void openImagePicker() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the selected image here
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                UserOperation.getInstance().changeImage(inputStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            finish();
            startActivity(getIntent());

        }
    }
}