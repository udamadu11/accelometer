package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
    ArrayList<Float> z = new ArrayList<Float>();
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
        if(z.size() == 10){
            sm.unregisterListener(this,accerometer);
        }
        list = findViewById(R.id.list);
        Log.d("Array List: ", String.valueOf(z));
        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, z);
        list.setAdapter(adapter);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
