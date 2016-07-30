package com.popoaichuiniu.jacy.loverunning;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.popoaichuiniu.jacy.Data.PlanData;
import com.popoaichuiniu.jacy.Other.AlarmStart;
import com.popoaichuiniu.jacy.views.EditDialog;
import com.popoaichuiniu.jacy.views.WeekSelect;

import java.util.ArrayList;

public class CreateNewPlan extends AppCompatActivity {
    public TextView  repeatDisplay=null;
    public TextView textViewLabel=null;
   public  String  time=null;
    public ArrayList<String> arrayList=new ArrayList<String>();

    String  music="xxxxxxxx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_plan);
        ImageView imageViewCreateNewPlanReturn= (ImageView) findViewById(R.id.image_revoke);
        final LinearLayout repeat= (LinearLayout) findViewById(R.id.repeat);
        LinearLayout label= (LinearLayout) findViewById(R.id.label);
        repeatDisplay= (TextView) findViewById(R.id.repeatDisplay);
        textViewLabel= (TextView) findViewById(R.id.label_text);
        ImageView  sure= (ImageView) findViewById(R.id.image_sure);
        final Switch switchButton= (Switch) findViewById(R.id.switch1);


        final TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
        time = timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
              time = timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
            }
        });


        /*点击确定时*/
         sure.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String repeatDisplayString=repeatDisplay.getText().toString();
                 repeatDisplayString=repeatDisplayString.substring(0,repeatDisplayString.length()-1);

                 ContentValues contentValues=new ContentValues();
                 contentValues.put("label",textViewLabel.getText().toString());
                 contentValues.put("time",time);
                 contentValues.put("repeat",repeatDisplayString.trim());
                 contentValues.put("vibrate",String.valueOf(switchButton.isChecked()));
                 contentValues.put("music",music);
                 contentValues.put("whetherRun","true");
                 long i= MainActivity.writeDataBase.insert("plan",null,contentValues);
                 if(i==-1)
                 {
                     Log.i("errorInsetPlan", "-1");
                     Toast.makeText(CreateNewPlan.this, "errorInsertPlan", Toast.LENGTH_SHORT).show();
                 }


                 Intent intent= new Intent(CreateNewPlan.this,PlanAlarm.class);
                   startActivity(intent);




             }
         });

        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog=new EditDialog(CreateNewPlan.this,R.style.Theme_dialog,R.layout.edittext_dialog,"确定","取消");
                final EditText editText= (EditText) editDialog.findViewById(R.id.edit_text);
                editText.setText(textViewLabel.getText());
                editDialog.setCanceledOnTouchOutside(false);

                /*加载dialog时*/
                editDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                editDialog.show();

            }
        });
        /*点击重复*/
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                WeekSelect weekSelect=new WeekSelect(CreateNewPlan.this, R.style.Theme_dialog,R.layout.dialog_week,"确定","取消");
                      weekSelect.setCanceledOnTouchOutside(false);
                weekSelect.show();
            }
        });
           /*点击撤销*/
         imageViewCreateNewPlanReturn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 CreateNewPlan.this.finish();
             }
         });
    }
}
