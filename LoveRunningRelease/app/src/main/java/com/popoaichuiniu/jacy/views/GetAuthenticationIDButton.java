package com.popoaichuiniu.jacy.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.popoaichuiniu.jacy.R;

/**
 * Created by jacy on 2016/2/2.
 */
public class GetAuthenticationIDButton extends Button {
    private int count=-1;
    private CountDownTimer countDownTimer=null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setCountDownTimer(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }

    public GetAuthenticationIDButton(Context context) {
        super(context);
        initialCountDownTimer();
        this.setText("获取验证码");



    }

    public GetAuthenticationIDButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialCountDownTimer();
        this.setText("获取验证码");

    }

    public GetAuthenticationIDButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialCountDownTimer();
        this.setText("获取验证码");

    }



    private void initialCountDownTimer()
    {
        countDownTimer =new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count--;
                GetAuthenticationIDButton.this.setText(count+"s后重新获取");

            }

            @Override
            public void onFinish() {
                GetAuthenticationIDButton.this.setBackgroundResource(R.drawable.dialog_button2_unclick);
                GetAuthenticationIDButton.this.setText("获取验证码");
                count=-1;
            }
        };
    }
}
