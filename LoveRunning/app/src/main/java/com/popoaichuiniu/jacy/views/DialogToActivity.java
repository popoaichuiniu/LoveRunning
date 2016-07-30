package com.popoaichuiniu.jacy.views;

/**
 * Created by jacy on 2015/11/29.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popoaichuiniu.jacy.loverunning.R;



/**
 * Created by jacy on 2015/11/16.
 */
public class DialogToActivity  extends Dialog implements View.OnClickListener {

    private Context context=null;
    private Class url=null;
    private boolean usingUrl=true;
    public DialogToActivity(Context context, int themeResId ,int layout,String suggestion, String yes ,String no,Class url,boolean usingUrl  ) {


        super(context, themeResId);
        this.context=context;
        this.url=url;
        this.usingUrl=usingUrl;


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
               if(usingUrl)
               {Intent intent=new Intent(context, url);
                   context.startActivity(intent);
               }
                this.dismiss();
                break;
            case R.id.button_dialogN:
                this.dismiss();
                break;
        }

    }
}