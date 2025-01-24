package com.gyaanguru.Tutorials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gyaanguru.Activities.NewsActivity;
import com.gyaanguru.R;

public class ArticleTutorialsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView javatpoint, geeksforgeeks, tutorialspoint, w3school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_tutorials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        javatpoint = (ImageView) findViewById(R.id.javatpoint);
        geeksforgeeks = (ImageView) findViewById(R.id.geeksforgeeks);
        tutorialspoint = (ImageView) findViewById(R.id.tutorialspoint);
        w3school = (ImageView) findViewById(R.id.w3school);

        javatpoint.setOnClickListener(this);
        geeksforgeeks.setOnClickListener(this);
        tutorialspoint.setOnClickListener(this);
        w3school.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent articleTutorialsIntent = new Intent(this, NewsActivity.class);
        if (view.getId() == R.id.javatpoint) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.javatpoint.com/");
        }
        else if (view.getId() == R.id.geeksforgeeks) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.geeksforgeeks.org/");
        }
        else if (view.getId() == R.id.tutorialspoint) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.tutorialspoint.com/");
        }
        else if (view.getId() == R.id.w3school) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.w3schools.com/");
        }
        startActivity(articleTutorialsIntent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}