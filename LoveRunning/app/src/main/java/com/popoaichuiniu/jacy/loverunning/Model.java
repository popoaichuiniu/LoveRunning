package com.popoaichuiniu.jacy.loverunning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Model extends AppCompatActivity implements AdapterView.OnItemClickListener {
   private Bundle bundle=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
         Log.i("ModelStart","xxxxxxxxxxx");
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        ImageView imageViewReturn = (ImageView) findViewById(R.id.image_return);

       bundle=new Bundle();
        bundle.putString("keyDisplay", "普通跑步");


        ArrayList<String> arr = new ArrayList<String>();
        arr.add("普通跑步");
        arr.add("计时跑步 >");
        arr.add("距离跑步 >");

        listView.setAdapter(new ListAdapterModel(this, arr));

        imageViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Model.this.finish();


            }
        });


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position)
        {
            case 0:
                bundle.putString("keyDisplay", "普通跑步");
                bundle.putInt("model", 0);
                Intent intentHome = new Intent(Model.this,MainActivity.class);
                intentHome.putExtra("bundle",bundle);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.i("model", "normal");
                Model.this.startActivity(intentHome);
                Log.i("model_after", "normal");

                break;
            case 1:
                 Intent intentTime =new Intent(this,TimeModel.class);
                  startActivity(intentTime);
                break;
            case 2:
                Intent intentDistance =new Intent(this,ModelDistance.class);
                 startActivity(intentDistance);
                break;
           default:
                throw new RuntimeException("出现非法选项");



        }


    }

    class ListAdapterModel extends BaseAdapter {

        private ArrayList<String> arr;

        public ListAdapterModel(Context context, ArrayList<String> arr) {

            this.arr = arr;
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout = null;
            if (convertView == null) {
                linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_model, null);

            } else {
                linearLayout = (LinearLayout) convertView;
            }
            TextView textView = (TextView) linearLayout.findViewById(R.id.list_item_model);
            textView.setText(arr.get(position));

            return linearLayout;
        }
    }
}
