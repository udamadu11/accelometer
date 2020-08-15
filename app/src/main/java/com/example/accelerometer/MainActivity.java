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
    TextView acceleration;
    ListView list;
    TextView textView;
    ArrayList<Float> z = new ArrayList<Float>();
    private Object SensorEventListener;
    float[] x = new float[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accerometer, SensorManager.SENSOR_DELAY_NORMAL);
        for(int i = 0; i < 10 ; i++) {
            acceleration = (TextView) findViewById(id.acceleration);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // acceleration.setText("\nX" + event.values[0]);
       // z.add(event.values[0]);
        //acceleration.setText("\n" + event.values[0]);
        for(int i = 0; i < 10 ; i++) {
            x[i] = event.values[0];
            acceleration.setText("\n" + x[i]);
            sm.unregisterListener(this,accerometer);
        }

        list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<String>();
        for(float s:x) {
            arrayList.add(String.valueOf(s));
        }
        Log.d("Array List: ", Arrays.toString(x));
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
