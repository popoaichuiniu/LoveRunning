package com.popoaichuiniu.jacy.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.popoaichuiniu.jacy.loverunning.CreateNewPlan;
import com.popoaichuiniu.jacy.loverunning.R;

/**
 * Created by jacy on 2015/12/3.
 */
public class WeekSelect extends Dialog implements View.OnClickListener {


    private Context context = null;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;


    public WeekSelect(Context context, int themeResId, int layout, String yes, String no) {


        super(context, themeResId);
        this.context = context;


        LinearLayout linearLayout = (LinearLayout) ((CreateNewPlan) context).getLayoutInflater().inflate(layout, null);
        Button buttonY = (Button) linearLayout.findViewById(R.id.button_dialogY);
        Button buttonN = (Button) linearLayout.findViewById(R.id.button_dialogN);
        checkBox1 = (CheckBox) linearLayout.findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox) linearLayout.findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox) linearLayout.findViewById(R.id.checkbox_3);
        checkBox4 = (CheckBox) linearLayout.findViewById(R.id.checkbox_4);
        checkBox5 = (CheckBox) linearLayout.findViewById(R.id.checkbox_5);
        checkBox6 = (CheckBox) linearLayout.findViewById(R.id.checkbox_6);
        checkBox7 = (CheckBox) linearLayout.findViewById(R.id.checkbox_7);


        buttonY.setText(yes);
        buttonN.setText(no);

        buttonN.setOnClickListener(this);
        buttonY.setOnClickListener(this);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width * 2 / 3.0), (int) (height / 2.0));
        getWindow().setContentView(linearLayout, params);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_dialogY:


                if (checkBox1.isChecked())
                    ((CreateNewPlan) context).arrayList.add("一");
                if (checkBox2.isChecked())
                    ((CreateNewPlan) context).arrayList.add("二");
                if (checkBox3.isChecked())
                    ((CreateNewPlan) context).arrayList.add("三");
                if (checkBox4.isChecked())
                    ((CreateNewPlan) context).arrayList.add("四");
                if (checkBox5.isChecked())
                    ((CreateNewPlan) context).arrayList.add("五");
                if (checkBox6.isChecked())
                    ((CreateNewPlan) context).arrayList.add("六");
                if (checkBox7.isChecked())
                    ((CreateNewPlan) context).arrayList.add("日");
                String repeatText = "";
                if (((CreateNewPlan) context).arrayList.size() == 0) {
                    repeatText = "只响一次>";
                } else if (((CreateNewPlan) context).arrayList.size() == 7) {
                    repeatText = "每天>";

                } else {
                    for (int i = 0; i < ((CreateNewPlan) context).arrayList.size(); i++) {
                        repeatText = repeatText + ((CreateNewPlan) context).arrayList.get(i) + "、";
                    }
                    repeatText = repeatText.substring(0, repeatText.length() - 1);
                    repeatText = repeatText + ">";

                }

                ((CreateNewPlan) context).repeatDisplay.setText(repeatText);


                this.dismiss();
                break;
            case R.id.button_dialogN:
                this.dismiss();
                break;
        }

    }

}
