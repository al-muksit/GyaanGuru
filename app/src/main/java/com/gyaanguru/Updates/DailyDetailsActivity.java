package com.gyaanguru.Updates;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gyaanguru.R;

public class DailyDetailsActivity extends AppCompatActivity {

    ImageView dailyUpdatesImage;
    TextView dailyUpdatesTitle, dailyUpdatesDescription;
    FloatingActionButton faButton;

    public static String TITLE = "";
    public static String DESCRIPTION = "";
    public static Bitmap IMAGE_BITMAP = null;

    TextToSpeech textToSpeech;
    private boolean isSpeaking = false; // Flag to track if TTS is currently speaking

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_details);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_grey));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dailyUpdatesImage = findViewById(R.id.dailyUpdatesImage);
        dailyUpdatesTitle = findViewById(R.id.dailyUpdatesTitle);
        dailyUpdatesDescription = findViewById(R.id.dailyUpdatesDescription);
        faButton = findViewById(R.id.faButton);

        dailyUpdatesTitle.setText(TITLE);
        dailyUpdatesDescription.setText(DESCRIPTION);
        if (IMAGE_BITMAP != null) {
            dailyUpdatesImage.setImageBitmap(IMAGE_BITMAP);
        }

        textToSpeech = new TextToSpeech(DailyDetailsActivity.this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                Log.d("TTS", "onInit: Success");
            } else {
                Log.e("TTS", "onInit: Failed");
            }
        });

        faButton.setOnClickListener(view -> {
            if (isSpeaking) {
                stopSpeech();   // Stop the speech if it's currently speaking
            } else {
                startSpeech();  // Start the speech if it's not speaking
            }
        });
    }

    private void startSpeech() {
        String text = dailyUpdatesDescription.getText().toString();
        int maxLength = 700; // Adjust this value as needed
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength) + "... (truncated)";
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        isSpeaking = true;
    }

    private void stopSpeech() {
        if (textToSpeech != null && textToSpeech.isSpeaking()) {
            textToSpeech.stop();
            isSpeaking = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSpeech();
        textToSpeech.shutdown();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopSpeech();
        textToSpeech.shutdown();
    }
}