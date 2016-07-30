package com.popoaichuiniu.jacy.views;

/**
 * Created by jacy on 2015/11/29.
 */


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.popoaichuiniu.jacy.Data.RunningRecord;
import com.popoaichuiniu.jacy.loverunning.MainActivity;
import com.popoaichuiniu.jacy.loverunning.R;

import java.text.DecimalFormat;
import java.util.Date;


/**
 * Created by jacy on 2015/11/16.
 */
public class SaveDialog extends Dialog implements View.OnClickListener {

    private Context context=null;

    public SaveDialog(Context context, int themeResId ,int layout,String suggestion, String yes ,String no ) {


        super(context, themeResId);
        this.context=context;



        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(layout,null);
        Button buttonY= (Button) linearLayout.findViewById(R.id.button_dialogY);
        Button buttonN= (Button) linearLayout.findViewById(R.id.button_dialogN);
        TextView textView= (TextView) linearLayout.findViewById(R.id.textView_dialog);
        buttonY.setText(yes);
        buttonN.setText(no);
        textView.setText(suggestion);
        buttonN.setOnClickListener(this);
        buttonY.setOnClickListener(this);
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width=windowManager.getDefaultDisplay().getWidth();
        int height=windowManager.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams((int)(width*2/3.0),(int)(height/4.0));
        getWindow().setContentView(linearLayout,params);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_dialogY:
                ((MainActivity) context).whetherEnd=true;


                 /*插入数据*/
                RunningRecord runningRecord=new RunningRecord(((MainActivity) context).currentUser
                                                              ,((MainActivity) context).locationCurrent
                                                              ,((MainActivity) context).runingDistance
                                                             ,((MainActivity) context).totalTime
                                                              ,"xxxxxx"
                                                             ,((MainActivity) context).runningSteps
                                                               ,0
                                                                  ,((MainActivity) context).runningStartTime);
                ContentValues contentValues=new ContentValues();
                 contentValues.put("username",runningRecord.getUsername());
                contentValues.put("runningStartTime",runningRecord.getRunningStartTime());
                contentValues.put("runningTime",(int)(runningRecord.getRunningTime()/60.0+0.5));
                contentValues.put("runningDistance",new DecimalFormat("0.000").format(runningRecord.getRunningDistance()/1000));
                contentValues.put("runningCalorie",runningRecord.getCalorie());
                contentValues.put("runningLocation",runningRecord.getRunningLocation());
                contentValues.put("runningPath",runningRecord.getRunningTrail());
                contentValues.put("runningSteps",runningRecord.getRunningSteps());
                long i= ((MainActivity) context).writeDataBase.insert("runRecord",null,contentValues);
                if(i==-1)
                {
                    Log.i("errorInsetRunRecord","-1");
                    Toast.makeText(context,"errorInsertRunRecord",Toast.LENGTH_SHORT).show();
                }

                /*刷新页面*/
                ((MainActivity) context).initialDrawerView();

                /*初始化信息*/
                ((MainActivity) context).runningSteps=0;
                ((MainActivity)context).runingDistance=0;
                ((MainActivity)context).totalTime=0;
                ((MainActivity)context).runningStartTime="";

                  /*初始化界面*/
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout behindLayout = (LinearLayout)(((MainActivity)context).findViewById(R.id.behind_layout));
                behindLayout.removeAllViews();
                behindLayout.addView(((MainActivity) context).behindLayoutBefore, layoutParams);
                ((MainActivity)context).baiduMap.clear();


                this.dismiss();
                break;
            case R.id.button_dialogN:
                this.dismiss();
                break;
        }



    }
}
