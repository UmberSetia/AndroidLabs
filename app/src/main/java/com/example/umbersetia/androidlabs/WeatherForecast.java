package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class WeatherForecast extends Activity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
    }
}
