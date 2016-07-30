package com.popoaichuiniu.jacy.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.popoaichuiniu.jacy.Data.PlanData;
import com.popoaichuiniu.jacy.Other.AlarmStart;
import com.popoaichuiniu.jacy.loverunning.MainActivity;
import com.popoaichuiniu.jacy.loverunning.PlanAlarm;
import com.popoaichuiniu.jacy.loverunning.R;

import java.util.ArrayList;

/**
 * Created by jacy on 2015/12/5.
 */
public class PlanAdapter extends BaseAdapter {
    ArrayList<PlanData> arr=null;
    Context context=null;
    public PlanAdapter(ArrayList<PlanData> arr,Context context ) {
        this.arr=arr;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        { viewHolder =new ViewHolder();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.plan_item,null);
            viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.checkbox_plan_item);

            viewHolder.textViewTime= (TextView) convertView.findViewById(R.id.time_text);
            viewHolder.textViewRepeat= (TextView) convertView.findViewById(R.id.repeat_text);
            viewHolder.textViewLabel= (TextView) convertView.findViewById(R.id.label_text);
            convertView.setTag(viewHolder);
            Log.i("convertViewNULL","xxxxxx");



        }
        else
        {
          viewHolder=(ViewHolder)convertView.getTag();
        }

        PlanData planData=arr.get(position);

         /*要先注册，因为有回收，所以覆盖之前的监听器*/
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    AlarmStart.registerService((PlanAlarm)context,arr.get(position).getTime(),arr.get(position).getPlanID(),arr.get(position).getRepeat());
                }
                else
                {
                    AlarmStart.cancelService(arr.get(position).getPlanID(),(PlanAlarm)context);
                }
                Toast.makeText(context,"闹钟个数:"+AlarmStart.getAlarmCount(),Toast.LENGTH_LONG).show();
                ContentValues contentValues = new ContentValues();
                contentValues.put("whetherRun", String.valueOf(isChecked));
                // Log.i("position", String.valueOf(position));
                //Log.i("isChecked", String.valueOf(isChecked));
                MainActivity.writeDataBase.update("plan", contentValues, "planID=?", new String[]{String.valueOf(arr.get(position).getPlanID())});

            }
        });
         //Log.i("planData",planData.getWhetherRun());
        viewHolder.checkBox.setChecked(Boolean.valueOf(planData.getWhetherRun()));
             viewHolder.textViewTime.setText(planData.getTime());
              viewHolder.textViewRepeat.setText(planData.getRepeat());
              viewHolder.textViewLabel.setText(planData.getLabel());


        return convertView;
    }
    public  class ViewHolder
    {
        public  CheckBox checkBox;
        public  TextView textViewTime;
        public  TextView textViewRepeat;
        public  TextView textViewLabel;
    }
}
