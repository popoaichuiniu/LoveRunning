package com.popoaichuiniu.jacy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.popoaichuiniu.jacy.loverunning.R;

/**
 * Created by jacy on 2015/11/23.
 */
public class AutoTextView extends TextView {
    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.AutoTextView);
        int  textColor=typedArray.getColor(R.styleable.AutoTextView_text_color, Color.WHITE);
         String  text=typedArray.getString(R.styleable.AutoTextView_text);
         float textSize=typedArray.getDimension(R.styleable.AutoTextView_text_size,30);
          typedArray.recycle();
        setText(text);
        setTextColor(textColor);
        setTextSize(textSize);

    }
}
