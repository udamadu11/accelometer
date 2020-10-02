package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<Float> mList1,mList2;
    ListView list,list2,list3;
    float currentValue = 0 , newValue = 0;
    ArrayList<Float> direction = new ArrayList<Float>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(R.id.list);
        list2 = findViewById(R.id.list2);
        list3 = findViewById(R.id.list3);

        SharedPreferences sharedPreferences = getSharedPreferences("accelerometer", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list1", null);
        String json2 = sharedPreferences.getString("list2",null);

        Type type = new TypeToken<ArrayList<Float>>() {}.getType();
        Type type2 = new TypeToken<ArrayList<Float>>() {}.getType();

        mList1 = gson.fromJson(json, type);
        mList2 = gson.fromJson(json2, type2);

        ArrayAdapter adapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_activated_1, mList1);
        list.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_activated_1, mList2);
        list2.setAdapter(adapter2);

        for(int i=0;i<10;i++){
//            currentValue = mList1.get(i);
            newValue = 5;
            direction.add(newValue);
        }


        SharedPreferences sp = getSharedPreferences("accelerometer",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gsonNew = new Gson();
        String json1 = gsonNew.toJson(direction);
        editor.putString("list3",json1);
        editor.commit();

        ArrayAdapter adapter3 = new ArrayAdapter<Float>(this,
                android.R.layout.simple_list_item_activated_1, direction);
        list3.setAdapter(adapter3);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                eraseData();
            }
        });
    }
    public void eraseData(){
        SharedPreferences settings = getSharedPreferences("accelerometer", MODE_PRIVATE);
        settings.edit().clear().commit();
        Intent ii = new Intent(MainActivity2.this, MainActivity2.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(ii);
        overridePendingTransition(0, 0);
    }


}