package com.popoaichuiniu.jacy.loverunning;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.popoaichuiniu.jacy.Adapter.PlanAdapter;
import com.popoaichuiniu.jacy.Data.PlanData;

import java.util.ArrayList;

public class PlanAlarm extends AppCompatActivity implements AdapterView.OnItemLongClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_alarm);
        ImageView imageViewReturnPlan = (ImageView) findViewById(R.id.image_return_plan);
        ImageView imageViewAdd= (ImageView) findViewById(R.id.image_add);
        ListView  listView= (ListView) findViewById(R.id.listView_plan);
        listView.setOnItemLongClickListener(this);
        ArrayList<PlanData> arr=new ArrayList<PlanData>();
        Cursor cursor = MainActivity.readDataBase.query("plan", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
             arr.add(new PlanData(cursor.getString(1)
                                  ,cursor.getString(5)
                                  ,cursor.getString(3)
                                  ,cursor.getString(2)
                                  ,cursor.getString(4)
                                   ,cursor.getString(6)
                                   ,cursor.getInt(0)));

        }
        PlanAdapter planAdapter=new PlanAdapter(arr,PlanAlarm.this);
          listView.setAdapter(planAdapter);
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlanAlarm.this,CreateNewPlan.class);
                startActivity(intent);

            }
        });
        imageViewReturnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PlanAlarm.this,MainActivity.class);
                intent.putExtra("notModel","notModel");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return false;
    }
}
