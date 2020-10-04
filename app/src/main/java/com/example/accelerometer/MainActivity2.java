package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<Float> mList1,mList2;
    ListView list,list2,list3;
    float currentValue = 0 , newValue = 0;
    ArrayList<String> direction = new ArrayList<String>(10);
    private static DecimalFormat df = new DecimalFormat("#.##");
    int view = R.layout.activity_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(R.id.list);
        list2 = findViewById(R.id.list2);
        list3 = findViewById(R.id.list3);

        //retrieve sharedPreferences data
        SharedPreferences sharedPreferences = getSharedPreferences("accelerometer", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list1", null);
        String json2 = sharedPreferences.getString("list2",null);

        Type type = new TypeToken<ArrayList<Float>>() {}.getType();
        Type type2 = new TypeToken<ArrayList<Float>>() {}.getType();

        mList1 = gson.fromJson(json, type);
        mList2 = gson.fromJson(json2, type2);

        // show acceleration data
        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_activated_1, mList1);
        list.setAdapter(adapter);

        //show time interval
        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_activated_1, mList2);
        list2.setAdapter(adapter2);

        //calculate direction indicator
        for(int i=0;i<10;i++){
           currentValue = mList1.get(i);
            newValue = currentValue - 0;
            df.setRoundingMode(RoundingMode.DOWN);
            float value = Float.valueOf(df.format(newValue));
            if(value > 0 ){
                direction.add("+" + value);
            }else{
                direction.add(""+value);
            }

        }

    //save direction data in sharePreferences
        SharedPreferences sp = getSharedPreferences("accelerometer",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gsonNew = new Gson();
        String json1 = gsonNew.toJson(direction);
        editor.putString("list3",json1);
        editor.commit();
    //show direction indicator data as listView
        ArrayAdapter adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, direction);
        list3.setAdapter(adapter3);

        //click button to erase data
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                eraseData();
            }
        });
    }
    public void eraseData(){
        //remove data for SharedPreferences
        SharedPreferences settings = getSharedPreferences("accelerometer", MODE_PRIVATE);
        settings.edit().clear().commit();

        //Refresh the screen
        refresh();
    }

    public void refresh(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);//Start the same Activity
        finish();
    }

}