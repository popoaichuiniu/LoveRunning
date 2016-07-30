package com.popoaichuiniu.jacy.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.popoaichuiniu.jacy.R;

/**
 * Created by jacy on 2016/1/28.
 */
public class BitmapHandle {

    public static  Bitmap changeBitmap(Context context,int ResId,int afterChangeWidth,int afterChangeHeight)
    {
        Bitmap bitmapBefore= BitmapFactory.decodeResource(context.getResources(), ResId);
        int width=bitmapBefore.getWidth();
        int height=bitmapBefore.getHeight();
        float scaleX=afterChangeWidth/(float)width;
        float scaleY=afterChangeHeight/(float)height;
        Matrix matrix=new Matrix();
        matrix.setScale(scaleX, scaleY);
        Bitmap bitmapAfter=Bitmap.createBitmap(bitmapBefore,0,0,width,height,matrix,true);
        return bitmapAfter;
    }
}
