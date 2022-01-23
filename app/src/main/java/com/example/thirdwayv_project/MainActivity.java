package com.example.thirdwayv_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startTrackingService();
        }
    }

    private void startTrackingService() {
        Intent intent = new Intent(this, TrackingService.class);
        startService(intent);
    }





    public void onClickRecord(View v) {
        // go to the record journey activity
        Intent journey = new Intent(MainActivity.this, RecordJourney.class);
        startActivity(journey);
    }

    public void onClickView(View v) {
//        // go to the activity for displaying journeys
//        Intent view = new Intent(MainActivity.this, ViewJourneys.class);
//        startActivity(view);
    }

    public void onClickStatistics(View v) {
//        // go to the activity for displaying statistics
//        Intent stats = new Intent(MainActivity.this, StatisticsActivity.class);
//        startActivity(stats);
    }
}