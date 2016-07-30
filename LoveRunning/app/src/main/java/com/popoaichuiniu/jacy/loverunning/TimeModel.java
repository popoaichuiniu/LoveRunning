package com.popoaichuiniu.jacy.loverunning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TimeModel extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Bundle bundle=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_model);
        ListView listView=(ListView)findViewById(R.id.listView_time);
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("10分钟");
        arr.add("20分钟");
        arr.add("30分钟");
        arr.add("1小时");
        arr.add("2小时");
        arr.add("自定义时间跑>");

        listView.setAdapter(new ListAdapterModel(this, arr));
        listView.setOnItemClickListener(this);


        ImageView imageViewReturn = (ImageView) findViewById(R.id.image_return_time);
        imageViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeModel.this.finish();

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentHome=new Intent(this,MainActivity.class);
        switch(position)
        {
            case 0:
                Log.i("timeModel", "10分钟");
                bundle.putString("keyDisplay", "10分钟跑步");
                bundle.putInt("model", -1);
                bundle.putInt("time", 10);
                Log.i("timeModel_1", "xxxxxxxxx");
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);
                Log.i("timeModel_2", "yyyyyyyy");

                break;
            case 1:
                bundle.putString("keyDisplay", "20分钟跑步");
                bundle.putInt("model", -1);
                bundle.putInt("time", 20);

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle",bundle);

                break;
            case 2:
                bundle.putString("keyDisplay", "30分钟跑步");
                bundle.putInt("model", -1);
                bundle.putInt("time", 30);

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);

                break;
            case 3:
                bundle.putString("keyDisplay", "一小时跑步");
                bundle.putInt("model", -1);
                bundle.putInt("time", 60);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);

                break;
            case 4:
            bundle.putString("keyDisplay", "二小时跑步");
            bundle.putInt("model", -1);
            bundle.putInt("time",120);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);
            break;
            case 5:
                bundle.putString("keyDisplay", "自定义跑步");//
                bundle.putInt("model", -1);//
                bundle.putInt("time",120);//

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle",bundle);

                break;
            default:
                throw new RuntimeException("出现非法选项");



        }

        startActivity(intentHome);


    }

    class ListAdapterModel extends BaseAdapter {
        private Context context;
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
            LinearLayout linearLayout=null;
            if (convertView==null)
            { linearLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_model,null);

            }
            else
            {
                linearLayout=(LinearLayout)convertView;
            }
            TextView textView= (TextView) linearLayout.findViewById(R.id.list_item_model);
            textView.setText(arr.get(position));

            return linearLayout;
        }
    }
}
