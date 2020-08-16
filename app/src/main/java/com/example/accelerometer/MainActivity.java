package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.example.accelerometer.R.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor accerometer;
    SensorManager sm;
    ListView list;
    ListView list2;
    ArrayList<Float> z = new ArrayList<Float>();
    ArrayList<Float> x = new ArrayList<Float>();
    private long timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        z.add(event.values[0]);

        x.add((float) (SystemClock.elapsedRealtime() /100000L ));
        if(z.size() == 10){
            sm.unregisterListener(this,accerometer);
        }

        list = findViewById(R.id.list);
        list2 = findViewById(id.list2);

        Log.d("Array List: ", String.valueOf(z));
        //Log.d("Array List: ", String.valueOf(timestamp));

        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, z);
        list.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, x);
        list2.setAdapter(adapter2);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
