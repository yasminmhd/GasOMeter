package com.spg0562.gasometer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int[] images = {R.drawable.news1, R.drawable.news2, R.drawable.news3};
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String[] newsUrls = {
            "https://www.channelnewsasia.com/asia/malaysia-ron95-fuel-price-lower-end-september-subsidy-citizens-5361036",
            "https://www.malaymail.com/news/malaysia/2025/10/07/govt-widens-budi95-fuel-aid-to-fishermen-boat-owners-and-new-drivers-over-31000-more-to-benefit/193789",
            "https://www.freemalaysiatoday.com/category/nation/2025/09/29/dont-panic-if-fuel-price-display-doesnt-show-rm1-99-malaysians-told"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView newsImageView = findViewById(R.id.newsImageView);

        newsImageView.setOnClickListener(v -> {
            String url = newsUrls[currentIndex];
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
            startActivity(browserIntent);
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % images.length;
                newsImageView.setImageResource(images[currentIndex]);
                handler.postDelayed(this, 3000); // Change every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);

        Button startButton = findViewById(R.id.startButton);

        // Go to second page when clicked
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
