package com.example.bookplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * Activity for displaying the selected book in real-world website
 *
 * @author Xiangji Kong
 */
public class ActivityWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // Hide system status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get the intent sent from the main activity
        String url = getIntent().getStringExtra("URL");

        // Capture the WebView and load the URL
        WebView web = (WebView) findViewById(R.id.web);
        web.loadUrl(url);
    }
}