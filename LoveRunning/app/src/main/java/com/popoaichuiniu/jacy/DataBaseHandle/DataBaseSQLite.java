package com.popoaichuiniu.jacy.DataBaseHandle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.popoaichuiniu.jacy.loverunning.MainActivity;

/**
 * Created by jacy on 2015/11/21.
 */
public class DataBaseSQLite extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "running.db";
   private  Context context=null;
    public DataBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context=context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLCreateTableUser="CREATE TABLE if not exists \"user\" (\n" +
                "\"username\"  VARCHAR2(20) NOT NULL,\n" +
                "\"password\"  VARCHAR2(40) NOT NULL,\n" +
                "\"name\"  VARCHAR2(30) NOT NULL,\n" +
                "\"personalSignature\"  VARCHAR2(50),\n" +
                "\"height\"  NUMERIC(6,3),\n" +
                "\"weight\"  NUMERIC(6,3),\n" +
                "\"headerPath\"  VARCHAR2(50),\n" +
                "\"totalDistance\"  NUMERIC(10,5),\n" +
                "\"totalTime\"  INTEGER,\n" +
                "\"longestDistance\"  NUMERIC(10,5),\n" +
                "\"longestTime\"  INTEGER,\n" +
                "\"totalSteps\"  INTEGER,\n" +
                "\"totalCalorie\"  INTEGER,\n" +
                "PRIMARY KEY (\"username\" ASC)\n" +
                ");\n";
        String SQLCreateTableRunRecord="CREATE TABLE if not exists \"runRecord\" (\n" +
                "\"username\"  VARCHAR2(20) NOT NULL,\n" +
                "\"recordID\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\"runningTime\"  INTEGER,\n" +
                "\"runningDistance\"  NUMERIC(10,5),\n" +
                "\"runningSteps\"  INTEGER,\n" +
                "\"runningCalorie\"  INTEGER,\n" +
                "\"runningPath\"  VARCHAR2(50),\n" +
                "\"runningStartTime\"  VARCHAR2(30),\n" +
                "\"runningLocation\"  VARCHAR2(30),\n" +
                "CONSTRAINT \"usernameRefer\" FOREIGN KEY (\"username\") REFERENCES \"user\" (\"username\")\n" +
                ");";
        String SQLCreatePlan="CREATE TABLE if not exists \"plan\" (\n" +
                "\"planID\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\"label\"  TEXT(50),\n" +
                "\"time\"  TEXT(40),\n" +
                "\"repeat\"  TEXT(20),\n" +
                "\"vibrate\"  TEXT(5),\n" +
                "\"music\"  TEXT(50),\n" +
                "\"whetherRun\"  TEXT(5)\n" +
                ");";
        String SQLCreateTriggerRunRecord1=
                "CREATE TRIGGER if not exists \"upatetotalDistance\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set totalDistance =totalDistance+new.runningDistance where username=new.username;\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;\n";


        String SQLCreateTriggerRunRecord2= "CREATE TRIGGER if not exists \"updateLongestTime\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set  longestTime = (select max(runningTime) from runRecord where username=new.username);\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;\n";


        String SQLCreateTriggerRunRecord3= "CREATE TRIGGER if not exists \"updateTotalSteps\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set totalSteps =totalSteps+new.runningSteps where username=new.username;\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;;\n";
        String SQLCreateTriggerRunRecord4= "CREATE TRIGGER  if not exists \"updateTotalTime\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set totalTime =totalTime+new.runningTime where username=new.username;\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;\n";
        String SQLCreateTriggerRunRecord5= "CREATE TRIGGER if not exists \"updateTotalCalorie\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set totalCalorie =totalCalorie+new.runningCalorie where username=new.username;\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;\n";
        String SQLCreateTriggerRunRecord6="CREATE TRIGGER \"updateLongestDistance\" AFTER INSERT ON \"runRecord\"\n" +
                "BEGIN\n" +
                " update user  set  longestDistance = (select max(runningDistance) from runRecord where username=new.username);\n" +
                "END\n" +
                ";;\n" +
                "DELIMITER ;";
        db.execSQL(SQLCreateTableUser);

        /*插入数据*/
        db.execSQL("INSERT INTO \"main\".\"user\" VALUES ('popoaichuiniu', 12345, '破破爱吹牛', '梦醒了，路还是要走的。', 162.7, 60, 'xxxxxxxxxx', 0, 0, 0, 0, 0, 0);");
        db.execSQL(SQLCreateTableRunRecord);

        db.execSQL(SQLCreatePlan);
        db.execSQL(SQLCreateTriggerRunRecord1);
        db.execSQL(SQLCreateTriggerRunRecord2);
        db.execSQL(SQLCreateTriggerRunRecord3);
        db.execSQL(SQLCreateTriggerRunRecord4);
        db.execSQL(SQLCreateTriggerRunRecord5);
        db.execSQL(SQLCreateTriggerRunRecord6);
        //Toast.makeText(context,"xxxxxx",Toast.LENGTH_SHORT).show();







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
