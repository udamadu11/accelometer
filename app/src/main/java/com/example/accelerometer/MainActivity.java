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
    Float v;

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
        list3 = findViewById(id.list3);
        float mul = 0;
        for(int i=0; i<x.size(); i++) {
           float num1 = z.get(i);
           float num2 = x.get(i);
           mul = num1 * num2;
           
        }
        y.add(mul);

        Log.d("Array List: ", String.valueOf(z));
        Log.d("Array List: ", String.valueOf(x));

        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, z);
        list.setAdapter(adapter);


        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, x);
        list2.setAdapter(adapter2);

        ArrayAdapter adapter3 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, y);
        list3.setAdapter(adapter3);

    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
