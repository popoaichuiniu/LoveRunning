package com.popoaichuiniu.jacy.Other;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.popoaichuiniu.jacy.loverunning.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by jacy on 2015/12/6.
 */
public class AlarmStart {
    private  static  AlarmManager alarmManager=null;
    private  static HashMap<Integer,ArrayList<PendingIntent>> hashMap=new HashMap<Integer,ArrayList<PendingIntent>>();

    public static   void registerService(Activity activity,String time,int ID,String repeat)
    {    alarmManager=(AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlarmReceiver.class);    //创建Intent对象
        String hourMinute []=time.split(":");
         if(hourMinute.length!=2)
         {
             throw new RuntimeException("时间格式有问题！");
         }
        repeat=repeat.trim();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinute[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));
        Log.i("repeat","*"+repeat+"*");

         if(repeat.equals("每天")) {
             PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
             ArrayList<PendingIntent> arr=new ArrayList<PendingIntent>();
             arr.add(pi);
             hashMap.put(ID, arr);
             Log.i("calendar", calendar.toString());
             if(System.currentTimeMillis()>calendar.getTimeInMillis())
             {
                 calendar.add(Calendar.DAY_OF_YEAR,1);
             }
             alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24 * 3600, pi);
             Log.i("alarm", "每天");
         }
        else if(repeat.equals("只响一次"))
         {PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
             ArrayList<PendingIntent> arr=new ArrayList<PendingIntent>();
             arr.add(pi);
             hashMap.put(ID, arr);
             Log.i("calendar", calendar.toString());
             if(System.currentTimeMillis()>calendar.getTimeInMillis())
             {
                 calendar.add(Calendar.DAY_OF_YEAR,1);
             }
             alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
             Log.i("alarm", "只响一次");
         }
        else {
             Log.i("alarm", "其他");
             String week []=repeat.split("、");
             for(int i=0;i<week.length;i++)
             {
                 Log.i("week","*"+week[i]+"*");
             }
             ArrayList<PendingIntent> arr=new ArrayList<PendingIntent>();
             int dayofweek=calendar.get(Calendar.DAY_OF_WEEK);//The firstday of a week in foreigner country is Sunday
             Log.i("dayofweek",""+dayofweek);
               for(int i=0;i<week.length;i++)
               {
                   if(week[i].equals("一")) {
                       Log.i("week", "*进入一*");
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR, (dayofweek - 2 + 7) % 7);
                      // Log.i("calendar", calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.CHINA) + " "+calendar.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.CHINA));
                           Log.i("calendar",calendar.getTime().toString());
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                               alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600, pi);
                       continue;
                   }
                   if(week[i].equals("二")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-3+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
                   if(week[i].equals("三")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-4+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
                   if(week[i].equals("四")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-5+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
                   if(week[i].equals("五")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-6+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
                   if(week[i].equals("六")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-7+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
                   if(week[i].equals("日")) {
                       PendingIntent pi = PendingIntent.getBroadcast(activity, 0, intent, 0);    //创建PendingIntent
                       arr.add(pi);
                       calendar.add(Calendar.DAY_OF_YEAR,(dayofweek-1+7)%7);
                       if(System.currentTimeMillis()>calendar.getTimeInMillis())
                       {
                           calendar.add(Calendar.DAY_OF_YEAR,7);
                       }
                       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 3600  , pi);
                       continue;
                   }
               }
             hashMap.put(ID, arr);

         }



        Toast.makeText(activity, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
    }
    public static  void cancelService(int ID,Activity activity)
    {
          if(alarmManager!=null)
          {  Log.i("alarm","取消闹钟");
              ArrayList<PendingIntent> arr=hashMap.get(ID);
              for(int i=0;i<arr.size();i++)
              alarmManager.cancel(arr.get(i));
              Toast.makeText(activity, "闹钟取消成功", Toast.LENGTH_LONG).show();//提示用户
              hashMap.remove(ID);
          }
    }
    public  static  int getAlarmCount()
    {
        return  hashMap.size();
    }

}
