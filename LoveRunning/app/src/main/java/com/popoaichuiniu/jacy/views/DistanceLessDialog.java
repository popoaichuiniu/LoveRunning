package com.popoaichuiniu.jacy.views;



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

        import com.popoaichuiniu.jacy.loverunning.MainActivity;
        import com.popoaichuiniu.jacy.loverunning.R;



/**
 * Created by jacy on 2015/11/16.
 */
public class DistanceLessDialog extends Dialog implements View.OnClickListener {

    private Context context=null;

    public DistanceLessDialog(Context context, int themeResId ,int layout,String suggestion, String yes ,String no ) {


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
                /*终止线程*/
                ((MainActivity)context).whetherEnd=true;
                /*初始化信息*/
                ((MainActivity)context).runningStartTime="";
                ((MainActivity)context).runningSteps=0;
                ((MainActivity)context).runingDistance=0;
                ((MainActivity)context).totalTime=0;

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
