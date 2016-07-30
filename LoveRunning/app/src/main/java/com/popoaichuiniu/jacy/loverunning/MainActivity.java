package com.popoaichuiniu.jacy.loverunning;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.popoaichuiniu.jacy.DataBaseHandle.DataBaseSQLite;
import com.popoaichuiniu.jacy.Other.MyLogPerfect;
import com.popoaichuiniu.jacy.views.CustomDialog;
import com.popoaichuiniu.jacy.views.DistanceLessDialog;
import com.popoaichuiniu.jacy.views.SaveDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SensorEventListener {

    /*控件*/
    Button button_model;
    Button button_start;
    Button button_plan;
    TextView textViewDistance = null;//距离textview
    TextView textViewSpeed = null;//速度textview
    TextView textViewStep = null;//步数textView
    private MapView mMapView = null;
    NavigationView navigationView = null;
    public LinearLayout behindLayoutBefore = null;

    /*定位*/
    public BaiduMap baiduMap = null;
    //private float mDirection;//方向
    private boolean isFirstIn;//是不是第一次进入到应用
    LocationClient mLocationClient = null;
    List<LatLng> points = new ArrayList<LatLng>();
    OverlayOptions ooPolyline;//


    private double mLatitude = 0;
    private double mLongtitude = 0;
    private double startLatitude = 0;
    private double startLongtitude = 0;
    public double runingDistance = 0;
    double distanceBetweenTwoPoints = 0;
    public int runningSteps = 0;
    public int totalTime = 0;
    int runningModel = 0;
    int purpose = 0;
    int progressStatus = 0;


    public String locationCurrent = null;
    public String currentUser = "popoaichuiniu";
    public String runningStartTime = "";


    boolean whetherStartTime = true;
    boolean whetherStartStep = true;
    boolean whetherStartDistance = true;
    boolean whetherStart = true;
    boolean whetherProgressbar = true;

    public boolean whetherEnd = false;

    boolean mutexSensor = true;








    /*传感器*/
   SensorManager mSensorManager = null;

    Vector<Float> sensorX = new Vector<Float>();
    Vector<Float> sensorY = new Vector<Float>();
    Vector<Float> sensorZ = new Vector<Float>();

    Vector<Float> sensorXTemp = new Vector<Float>();
    Vector<Float> sensorYTemp = new Vector<Float>();
    Vector<Float> sensorZTemp = new Vector<Float>();

    Handler handler = new Handler();
    Handler handlerSteps = new Handler();
    Handler handelDraw = new Handler();
    Handler handlerProgressbar = new Handler();





    public static DataBaseSQLite dataBaseSQLite = null;
    public static  SQLiteDatabase readDataBase = null;
    public  static SQLiteDatabase writeDataBase = null;



    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");




    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    private void judgeGPS() {
        LocationManager locationManager
                = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps || !network) {
            CustomDialog dialog = new CustomDialog(this, R.style.Theme_dialog, R.layout.custom_dialog, "此应用需要定位，请开启网络定位和GPS定位", "去设置", "取消", Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }


    }

    private void judgeLineAccelerateSensor() {


    }


    /*按钮点击*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_model:
                Intent intent = new Intent(this, Model.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.schedule:
                Intent intentS = new Intent(this, PlanAlarm.class);
                startActivityForResult(intentS, 0);
                break;
            case R.id.start:
                /*切换界面*/
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                LinearLayout behindLayout = (LinearLayout) findViewById(R.id.behind_layout);
                behindLayout.removeAllViews();
                LinearLayout behindLayoutAfter = (LinearLayout) getLayoutInflater().inflate(R.layout.running, null);
                behindLayout.addView(behindLayoutAfter, layoutParams);

                whetherEnd = false;
                runningStartTime = simpleDateFormat.format(new Date());

                /*progressbar */
                final ProgressBar progressBar = (ProgressBar) behindLayoutAfter.findViewById(R.id.progressBar);
                if (runningModel == 0)
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                else {
                    progressBar.setMax(purpose);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                if (whetherEnd) {
                                    break;
                                }

                                if (runningModel == -1) {
                                    progressStatus = totalTime;
                                } else if (runningModel == 1) {
                                    progressStatus = (int) runingDistance;
                                }
                                if (progressStatus >= purpose) {

                                }

                                if (whetherProgressbar) {
                                    handlerProgressbar.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            progressBar.setProgress(progressStatus);
                                        }
                                    });
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }).start();
                }





                 /*暂停继续按钮*/
                final Button buttonPause = (Button) behindLayoutAfter.findViewById(R.id.pause);

                  /*时间记录textView*/
                final TextView textViewTime = (TextView) behindLayoutAfter.findViewById(R.id.time);

                    /*距离textView*/
                textViewDistance = (TextView) behindLayoutAfter.findViewById(R.id.running_distance);


                 /*速度textView*/
                textViewSpeed = (TextView) behindLayoutAfter.findViewById(R.id.speed);

                 /*脚步textView*/
                textViewStep = (TextView) behindLayoutAfter.findViewById(R.id.step);





                /*暂停继续按钮*/
                buttonPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (whetherStart == false) {
                            whetherStart = true;
                            whetherStartTime = true;
                            whetherStartDistance = true;
                            whetherStartStep = true;
                            whetherProgressbar = true;

                            buttonPause.setText("暂停");
                            buttonPause.setBackgroundResource(R.drawable.pause_selector);


                        } else {
                            whetherStart = false;
                            whetherStartTime = false;
                            whetherStartDistance = false;
                            whetherStartStep = false;
                            whetherProgressbar = false;
                            buttonPause.setText("继续");
                            buttonPause.setBackgroundResource(R.drawable.continue_selector);

                        }
                    }
                });

                  /*步数textView更新*/
                runningSteps = 0;
                drawSteps();

               /*时间记录textView更新*/
                totalTime = 0;
                new Thread(new Runnable() {
                    private String display = "";
                    private int hour = 0;
                    private int min = 0;
                    private int second = 0;
                    private String hourS = "";
                    private String minS = "";
                    private String secondS = "";

                    @Override
                    public void run() {

                        while (true) {
                            if (whetherEnd) {
                                break;
                            }
                            if (whetherStartTime) {
                                display = "";
                                secondS = "";
                                minS = "";
                                hourS = "";
                                second++;
                                totalTime++;
                                if (second >= 60) {
                                    min++;
                                    second = 0;
                                }
                                if (min >= 60) {
                                    min = 0;
                                    hour++;
                                }
                                if (hour >= 60) {
                                    second = 0;
                                    min = 0;
                                    hour = 0;


                                }

                                if (hour < 10)
                                    hourS = hourS + "0" + hour;
                                else
                                    hourS = hourS + hour;
                                if (min < 10)
                                    minS = minS + "0" + min;
                                else
                                    minS = minS + min;
                                if (second < 10)
                                    secondS = secondS + "0" + second;
                                else
                                    secondS = secondS + second;

                                display = hourS + ":" + minS + ":" + secondS;

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        textViewTime.setText(display);

                                    }
                                });


                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                /* 绘制跑步路线和速度,距离*/
                points.clear();
                runingDistance = 0;
                drawPath();





                /*结束按钮*/
                Button buttonFinish = (Button) behindLayoutAfter.findViewById(R.id.end);
                buttonFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (runingDistance < 50) {
                            DistanceLessDialog distanceLessDialog = new DistanceLessDialog(MainActivity.this, R.style.Theme_dialog, R.layout.custom_dialog, "跑步距离太短，无法记录", "不跑了", "继续跑步");
                            distanceLessDialog.setCanceledOnTouchOutside(false);
                            distanceLessDialog.show();
                        } else {
                            SaveDialog saveDialog = new SaveDialog(MainActivity.this, R.style.Theme_dialog, R.layout.custom_dialog, "你已经尽力了？", "结束并保存", "继续跑步");
                            saveDialog.setCanceledOnTouchOutside(false);
                            saveDialog.show();
                        }


                    }
                });
        }
    }

    /*刷新步数*/
    private void drawSteps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (whetherEnd) {
                        break;
                    }
                    if (whetherStartStep) {
                        if (mutexSensor) {
                            mutexSensor = false;
                            sensorXTemp.addAll(sensorX);
                            sensorYTemp.addAll(sensorY);
                            sensorZTemp.addAll(sensorZ);
                            mutexSensor = true;

                        }


                        Log.i("sensorSizeXBefore", String.valueOf(sensorXTemp.size()));
                        if (sensorXTemp.size() > 100) {

                            sensorXTemp.clear();
                            sensorXTemp.clear();
                            sensorXTemp.clear();
                            sensorX.clear();
                            sensorY.clear();
                            sensorZ.clear();


                        }
                        Log.i("sensorSizeX", String.valueOf(sensorX.size()));
                        if (sensorXTemp.size() > 0) {
                            MyLogPerfect myLogPerfectX = new MyLogPerfect("xSensor.txt");
                            MyLogPerfect myLogPerfectY = new MyLogPerfect("ySensor.txt");
                            MyLogPerfect myLogPerfectZ = new MyLogPerfect("zSensor.txt");
                            for (int i = 0; i < sensorXTemp.size(); i++) {
                                myLogPerfectX.i("xsensorData:", String.valueOf(sensorXTemp.get(i)));
                            }
                            for (int i = 0; i < sensorYTemp.size(); i++) {
                                myLogPerfectY.i("ysensorData:", String.valueOf(sensorYTemp.get(i)));
                            }
                            for (int i = 0; i < sensorZTemp.size(); i++) {
                                myLogPerfectZ.i("zsensorData:", String.valueOf(sensorZTemp.get(i)));
                            }
                            float maxX = 0;
                            float minX = 0;
                            float maxY = 0;
                            float minY = 0;
                            float maxZ = 0;
                            float minZ = 0;


                            maxX = Collections.max(sensorXTemp);
                            minX = Collections.min(sensorXTemp);
                            maxY = Collections.max(sensorYTemp);
                            minY = Collections.min(sensorYTemp);
                            maxZ = Collections.max(sensorZTemp);
                            minZ = Collections.min(sensorZTemp);


                            Log.i("sensorM", "Max :" + maxX + "Min :" + minX);
                            Log.i("sensorM", "Max :" + maxY + "Min :" + minY);
                            Log.i("sensorM", "Max :" + maxZ + "Min :" + maxZ);


                            if ((Math.abs(maxX + minX) < 2) && (maxX - minX) > 1) {
                                runningSteps = runningSteps + 1;
                                Log.i("success", "X:" + maxX + " " + minX);
                            } else if ((Math.abs(maxY + minY) < 2) && (maxY - minY) > 1)//左边越大越灵敏，右边越小越灵敏                        {
                            {
                                runningSteps = runningSteps + 1;
                                Log.i("success", "Y:" + maxY + " " + minY);
                            } else if ((Math.abs(maxZ + minZ) < 2) && (maxZ - minZ) > 1) {
                                runningSteps = runningSteps + 1;
                                Log.i("success", "Z:" + maxZ + " " + minZ);
                            }


                            sensorXTemp.clear();
                            sensorYTemp.clear();
                            sensorZTemp.clear();

                            sensorX.clear();
                            sensorY.clear();
                            sensorZ.clear();


                            handlerSteps.post(new Runnable() {
                                @Override
                                public void run() {
                                    //
                                    textViewStep.setText(String.valueOf(runningSteps));

                                }
                            });

                        }


                    }
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        ).start();

    }

    /*绘制路线*/
    private void drawPath() {
        startLatitude = mLatitude;
        startLongtitude = mLongtitude;
        points.add(new LatLng(startLatitude, startLongtitude));//加第一个点

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (whetherEnd) {
                        break;
                    }
                    // Log.i("ToastBefore", "xxxxx");
                    if (whetherStartDistance) {

                        points.add(new LatLng(mLatitude, mLongtitude));
                        distanceBetweenTwoPoints = DistanceUtil.getDistance(points.get(0), points.get(1));
                        if (distanceBetweenTwoPoints > 30) {
                            try {
                                points.remove(1);
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        runingDistance = runingDistance + distanceBetweenTwoPoints;
                        handelDraw.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(MainActivity.this, "纬度：" + mLatitude + "   经度：" + mLongtitude, Toast.LENGTH_SHORT).show();
                                ooPolyline = new PolylineOptions().width(10).color(getResources().getColor(R.color.colorAccent))
                                        .points(points);//设置折线属性
                                baiduMap.addOverlay(ooPolyline);//添加在地图中

                                //更新distance
                                textViewDistance.setText(String.valueOf(runingDistance / 1000));


                                //更新speed
                                textViewSpeed.setText(String.valueOf(new DecimalFormat("0.00").format(distanceBetweenTwoPoints / 1.8)));


                            }

                        });
                        try {

                            Thread.sleep(1500);
                            points.remove(0);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        points.remove(0);
                        points.add(new LatLng(mLatitude, mLongtitude));
                        try {

                            Thread.sleep(1500);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();

    }

    /*初始化界面*/
    private void initialView() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);


         /*界面左侧滑动，toolbar 等设置*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//toolbar 是用来代替actionbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);// ActionBarDrawerToggle实现了DrawerListener
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*网络不可用  dialog*/
        if (!isNetworkAvailable(this)) {
            CustomDialog dialog = new CustomDialog(this, R.style.Theme_dialog, R.layout.custom_dialog, "当前网络不可用", "去设置", "取消", Settings.ACTION_SETTINGS);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

         /*GPS定位  网络定位 dialog*/
        judgeGPS();


        /*地图控件的初始化*/
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(19);//设置map的放大级别
        mMapView = (MapView) findViewById(R.id.bmapView); //获取地图控件引用
        baiduMap = mMapView.getMap();
        baiduMap.setMapStatus(mapStatusUpdate);//设置map状态
        baiduMap.setMyLocationEnabled(true);//开启定位图层
        isFirstIn = true;//设置成第一次进入


        /*跑步模式按钮*/
        button_model = (Button) findViewById(R.id.button_model);
        SetButtonModel();//设置跑步模式的文字
        button_model.setOnClickListener(this);

        /*开始按钮*/
        button_start = (Button) findViewById(R.id.start);
        button_start.setOnClickListener(this);


        /*计划按钮*/
        button_plan = (Button) findViewById(R.id.schedule);
        button_plan.setOnClickListener(this);

        /*behindLayoutBefore*/
        behindLayoutBefore = (LinearLayout) findViewById(R.id.behind_layout_before);


    }


    /*初始化数据库*/
    private void initialDataBase() {
        dataBaseSQLite = new DataBaseSQLite(this, "running.db", null, 1);
        Toast.makeText(this, "创建数据库成功", Toast.LENGTH_SHORT).show();
        readDataBase = dataBaseSQLite.getReadableDatabase();
        writeDataBase = dataBaseSQLite.getWritableDatabase();
    }

    /*初始化左侧界面*/
    public void initialDrawerView() {

        MenuItem menuItemTotalDistance = navigationView.getMenu().findItem(R.id.total_distance);
        MenuItem menuItemTotalTime = navigationView.getMenu().findItem(R.id.total_time);
        MenuItem menuItemLongestDistance = navigationView.getMenu().findItem(R.id.longest_distance);
        MenuItem menuItemLongestTime = navigationView.getMenu().findItem(R.id.longest_time);

        String[] columns = new String[]{"totalDistance", "totalTime", "longestDistance", "longestTime"};
        Cursor cursor = readDataBase.query("user", columns, "username=?", new String[]{currentUser}, null, null, null);
        while (cursor.moveToNext()) {
            menuItemTotalDistance.setTitle("累计距离" + "                    " + cursor.getFloat(0) + "公里");
            menuItemTotalTime.setTitle("累计时间" + "                    " + cursor.getInt(1) + "分钟");
            menuItemLongestDistance.setTitle("最长距离" + "                    " + cursor.getFloat(2) + "公里");
            menuItemLongestTime.setTitle("最长时间" + "                    " + cursor.getInt(3) + "分钟");
        }
    }


    //设置跑步模式的文字
    private void SetButtonModel() {
        Intent intentReturn = getIntent();
        if (intentReturn.getAction() != "android.intent.action.MAIN"&&intentReturn.getStringExtra("notModel")==null) {
            Bundle bundleData = intentReturn.getBundleExtra("bundle");
            if (bundleData == null) {
                throw new RuntimeException("数据传输错误"+intentReturn.toString());
            } else {
                int model = bundleData.getInt("model");
                String keyDisplay = bundleData.getString("keyDisplay") + " >";
                Log.i("model", String.valueOf(model));
                Log.i("keyDisplay", keyDisplay);

                if (keyDisplay == null) {
                    throw new RuntimeException("跑步模式出现异常");
                } else {
                    button_model.setText(keyDisplay);
                    runningModel = model;
                    switch (runningModel) {
                        case 0:
                            purpose = 0;
                            break;
                        case -1:
                            purpose = bundleData.getInt("time", -1) * 60;
                            break;
                        case 1:
                            purpose = bundleData.getInt("distance", -1);
                            break;
                        default:
                            throw new RuntimeException("跑步模式出现异常");


                    }
                    if (purpose == -60 || purpose == -1) {
                        throw new RuntimeException("跑步模式de数据传递出现错误");
                    }

                }

            }

        }
    }

    private void initialLocation() {
        mLocationClient = new LocationClient(this);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setCoorType("bd09ll");//设置坐标系
        locationClientOption.setIsNeedAddress(true);//设置需要地址
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // locationClientOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        locationClientOption.setScanSpan(1001);//设置获得定位间隔
        locationClientOption.setOpenGps(true);//设置打开GPS定位
        //locationClientOption.setLocationNotify(true);////可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationClientOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mLocationClient.setLocOption(locationClientOption);//设置参数
        mLocationClient.registerLocationListener(new MyListner());

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if (mutexSensor) {
            mutexSensor = false;
            if (sensorX.size() < 200) {

                sensorX.add(event.values[0]);
                sensorY.add(event.values[1]);
                sensorZ.add((event.values[2]));


            }
            mutexSensor = true;
        }

        Log.i("SensorData", "X:" + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private class MyListner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            //获取位置数据
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // .direction(bdLocation.getDirection())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .speed(bdLocation.getSpeed())
                    .build();


            //在地图显示我的位置
            baiduMap.setMyLocationData(myLocationData);


            //实时更新经纬度
            mLatitude = bdLocation.getLatitude();
            mLongtitude = bdLocation.getLongitude();
            //获得跑步地点
            if (locationCurrent == null)
                locationCurrent = bdLocation.getLocationDescribe();



           /*打印调试信息*/
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(bdLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(bdLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(bdLocation.getRadius());
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(bdLocation.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(bdLocation.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(bdLocation.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
            List<Poi> list = bdLocation.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());


            //第一次进入就将地图中心设置到自己的位置
            if (isFirstIn) {
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
                isFirstIn = false;
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialView();
        initialLocation();
        initialDataBase();
        //数据库调试数据
      /*  ContentValues contentValues=new ContentValues();
        contentValues.put("username",currentUser);
        contentValues.put("runningStartTime","2015-10-30 14:00");
        contentValues.put("runningTime",70);
        contentValues.put("runningDistance",1000.000);
        contentValues.put("runningCalorie", 100000);
        contentValues.put("runningLocation", "anhuishengxuanchengshi");
        contentValues.put("runningPath", "xxxxxxxxxxxxx");
        contentValues.put("runningSteps", 12345);
        long i= writeDataBase.insert("runRecord",null,contentValues);
        if(i==-1)
        {
            Log.i("errorInset","-1");
            Toast.makeText(this,"errorInset",Toast.LENGTH_SHORT).show();
        }*/
        initialDrawerView();

        //获取传感器服务
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.start();

    }

    @Override
    protected void onStop() {
        // Log.i("stop","xxxx");
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        //关闭定位
        mLocationClient.stop();
        //关闭 定位图层
        baiduMap.setMyLocationEnabled(false);

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

        //解除监听传感器数据
        mSensorManager.unregisterListener(this);

        //Log.i("destroy", "xxxx");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        Sensor mSensorAccelerate = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Log.i("sensorAccelerate:", mSensorAccelerate.getName());
        mSensorManager.registerListener(this, mSensorAccelerate, SensorManager.SENSOR_DELAY_NORMAL);


       /* Sensor mSensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Log.i("sensorGravity:", mSensorGravity.getName());
        mSensorManager.registerListener(this,mSensorGravity,SensorManager.SENSOR_DELAY_GAME);*/


        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();


    }

    @Override
    protected void onPause() {

        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

        super.onPause();


    }


    /*点击后退按钮*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }


    /*设置菜单的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.id_normal:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为普通地图
                break;
            case R.id.id_satellite:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
                break;
            case R.id.id_mylocation:
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(mLatitude, mLongtitude)));

        }
        return super.onOptionsItemSelected(item);
    }


    /*设置左侧滑动窗口按钮的点击*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /////异步任务
    class UpdateTextTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;

        UpdateTextTask(Context context) {
            this.context = context;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {

        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            int i = 0;
            while (i < 10) {
                i++;
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            // Toast.makeText(context, "执行完毕", Toast.LENGTH_SHORT).show();
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            System.out.println("do onProgressUpdate.........");
            //  tv.setText("" + values[0]);
        }
    }
}

