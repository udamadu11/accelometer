package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.accelerometer.R.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor accerometer;
    SensorManager sm;
    ListView list,list2,list3;

    ArrayList<Float> z = new ArrayList<Float>(10);
    ArrayList<Float> x = new ArrayList<Float>(10);
    ArrayList<Float> v = new ArrayList<Float>(10);

    ArrayList<Float> velocity = new ArrayList<Float>(10);

    float current = 0,prev = 0,interval = 0, velosity = 0;
    Float nanosecond = 1.0f / 1000000.0f;
    float mul = 0, inter;
    private static DecimalFormat df = new DecimalFormat("0");
    private long timestamp;

    // called activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //save data in sharedPreferences
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private  void saveData(){
        SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(x);
        editor.putString("list",json);
        editor.commit();

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        z.add(event.values[0]); // add to acceleration into z arrayList
        x.add((float)event.timestamp); // add timestamp into x arrayList
        if(z.size() == 10){ //adding acceleration value until 10
            sm.unregisterListener(this,accerometer);
        }

        list = findViewById(R.id.list);
        list2 = findViewById(id.list2);
        list3 = findViewById(id.list3);

        getInterval(); // call getInterval() to count time interval
        getVelocity(); // call getVelocity?() to count velocity

//        Log.d("Array List z: ", String.valueOf(z));
//        Log.d("Array List x: ", String.valueOf(x));
//        Log.d("Array List v: ", String.valueOf(v));
//        Log.d("Array List velocity: ", String.valueOf(velocity));

        // print acceleration arrayList value into mobile display
        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, z);
        list.setAdapter(adapter);

        // print time interval arrayList value into mobile display
        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, v);
        list2.setAdapter(adapter2);

        // print velocity arrayList value into mobile display
        ArrayAdapter adapter3 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_1, velocity);
        list3.setAdapter(adapter3);

    }

// get time interval into arrayList
    public void getInterval(){
        for(int i=0; i<x.size(); i++) {
            current = x.get(i); // get arrayList i value
            if(prev == 0) prev = current; // check previous value is 0 or not
            interval = (current - prev) * nanosecond; // count interval and convert to nanoSecond
            df.setRoundingMode(RoundingMode.UP); //round value
            prev = current; // current value assign to previous value
        }
        v.add(Float.valueOf(df.format(interval))); // add interval to v arrayList
    }

    // count velocity
    public void getVelocity(){
        for(int i=0; i<x.size(); i++) {
            float acc = z.get(i); // get acceleration value from arrayList
            float in = v.get(i); // get intervals  from v arrayList
            velosity = in * acc; // count velocity (acceleration * time interval )
        }
        velocity.add(velosity); // add velocity to velocity arrayList
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
