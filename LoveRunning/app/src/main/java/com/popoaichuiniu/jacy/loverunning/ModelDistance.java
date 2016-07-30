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

public class ModelDistance extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private  Bundle bundle=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_distance);
        ListView listView = (ListView) findViewById(R.id.listView_distance);
        ImageView imageViewReturn = (ImageView) findViewById(R.id.image_return_distance);
        imageViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelDistance.this.finish();


            }
        });


        ArrayList<String> arr = new ArrayList<String>();

        arr.add("800米跑");
        arr.add("1000米跑步");
        arr.add("3000米跑步");
        arr.add("5000米跑步");
        arr.add("自定义距离跑步");
        listView.setAdapter(new ListAdapterModel(this, arr));
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentHome=new Intent(this,MainActivity.class);
        switch(position)
        {
            case 0:

                bundle.putString("keyDisplay", "800米跑步");
                bundle.putInt("model", 1);
                bundle.putInt("distance", 800);

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);


                break;
            case 1:
                bundle.putString("keyDisplay", "1000米跑步");
                bundle.putInt("model", 1);
                bundle.putInt("distance", 1000);

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle",bundle);

                break;
            case 2:
                bundle.putString("keyDisplay", "3000米跑步");
                bundle.putInt("model", 1);
                bundle.putInt("distance", 3000);

                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);

                break;
            case 3:
                bundle.putString("keyDisplay", "5000米跑步");
                bundle.putInt("model", 1);
                bundle.putInt("distance", 5000);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);

                break;
            case 4:
                bundle.putString("keyDisplay", "自定义距离跑步");/////
                bundle.putInt("model", 1);
                bundle.putInt("distance", 1000);///
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.putExtra("bundle", bundle);
                break;

            default:
                throw new RuntimeException("出现非法选项");



        }

        startActivity(intentHome);


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
