package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.accelerometer.R.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor accerometer;
    SensorManager sm;
    ListView list,list2,list3;
    ArrayList<Float> z = new ArrayList<Float>(10);
    ArrayList<Float> x = new ArrayList<Float>(10);
    ArrayList<Float> y = new ArrayList<Float>(10);
    ArrayList<Float> v = new ArrayList<Float>(10);
    ArrayList<Float> velocity = new ArrayList<Float>(10);
    float current = 0,prev = 0,interval = 0, velosity = 0;
    Float nanosecond = 1.0f / 1000000.0f;
    float mul = 0;

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
        x.add((float)event.timestamp);
        if(z.size() == 10){
            sm.unregisterListener(this,accerometer);
        }

        list = findViewById(R.id.list);
        list2 = findViewById(id.list2);
        list3 = findViewById(id.list3);

        getV();
        getInterval();
        getVelocity();

        Log.d("Array List z: ", String.valueOf(z));
        Log.d("Array List x: ", String.valueOf(x));
        Log.d("Array List v: ", String.valueOf(v));
        Log.d("Array List velocity: ", String.valueOf(velocity));

        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, z);
        list.setAdapter(adapter);


        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, v);
        list2.setAdapter(adapter2);

        ArrayAdapter adapter3 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, y);
        list3.setAdapter(adapter3);

    }


    public void getInterval(){
        for(int i=0; i<x.size(); i++) {
            current = x.get(i);
            if(prev == 0) prev = current;
            interval = (current - prev) * nanosecond;
            prev = current;
        }
        v.add(interval);
    }
    public void getV(){
        for(int i=0; i<x.size(); i++) {
            float num1 = z.get(i);
            float num2 = x.get(i);
            mul = num1 * num2;

        }
        y.add(mul);
    }
    public void getVelocity(){
        for(int i=0; i<x.size(); i++) {
            float in = z.get(i); // get time intervals
            float acc = v.get(i); // get acceration from arraylist
            velosity = in * acc;
        }
        velocity.add(velosity);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
