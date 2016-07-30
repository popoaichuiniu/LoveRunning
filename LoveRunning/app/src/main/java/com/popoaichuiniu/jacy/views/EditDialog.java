package com.popoaichuiniu.jacy.views;

/**
 * Created by jacy on 2015/12/4.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popoaichuiniu.jacy.loverunning.CreateNewPlan;
import com.popoaichuiniu.jacy.loverunning.R;



/**
 * Created by jacy on 2015/11/16.
 */
public class EditDialog extends Dialog implements View.OnClickListener {

    private Context context=null;
    EditText editText=null;

    public EditDialog(Context context, int themeResId ,int layout, String yes ,String no ) {


        super(context, themeResId);

       this.context=context;
        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(layout, null);
        Button buttonY= (Button) linearLayout.findViewById(R.id.button_dialogY);
        Button buttonN= (Button) linearLayout.findViewById(R.id.button_dialogN);
         editText= (EditText) linearLayout.findViewById(R.id.edit_text);





        buttonY.setText(yes);
        buttonN.setText(no);

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
                ((CreateNewPlan)context).textViewLabel.setVisibility(View.VISIBLE);
                ((CreateNewPlan)context).textViewLabel.setText(editText.getText());



                this.dismiss();
                break;
            case R.id.button_dialogN:
                this.dismiss();
                break;
        }

    }
}
