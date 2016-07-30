package com.popoaichuiniu.jacy.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by jacy on 2016/1/28.
 */
public  class GetScreenSize {
    private  WindowManager windowManager;
    private int widthScreen ;  // 屏幕宽度（像素）
    private int heightScreen ;  // 屏幕高度（像素）
    public GetScreenSize(Context context)
    {
        windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();

       windowManager.getDefaultDisplay().getMetrics(metric);
         widthScreen = metric.widthPixels;  // 屏幕宽度（像素）
        heightScreen = metric.heightPixels;  // 屏幕高度（像素）
    }
    public int getScreenWidth()
    {
        return widthScreen;
    }
    public int getScreenHeight()
    {
        return heightScreen;
    }
}
