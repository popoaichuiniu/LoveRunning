package com.popoaichuiniu.jacy.loverunning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        TextView textViewTime= (TextView) findViewById(R.id.time_current);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
         textViewTime.setText(simpleDateFormat.format(new Date()));

    }
}
