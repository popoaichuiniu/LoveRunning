package com.popoaichuiniu.jacy.Data;

import java.util.Date;

/**
 * Created by jacy on 2015/11/21.
 */
public class RunningRecord {
      private  String username="popoaichuiniu";


       private String runningLocation;
       private   double  runningDistance;
        private   int     runningTime;
        private  String  runningTrail;
        private  int runningSteps=0;
        private   float  calorie=0;
         private  String  runningStartTime=null;

    public RunningRecord(String username) {
        this.username = username;
    }

    public RunningRecord(String username,  String runningLocation, double runningDistance, int runningTime, String runningTrail, int runningSteps, float calorie,String runningStartTime) {
        this.username = username;

        this.runningLocation = runningLocation;
        this.runningDistance = runningDistance;
        this.runningTime = runningTime;
        this.runningTrail = runningTrail;
        this.runningSteps = runningSteps;
        this.calorie = calorie;
        this.runningStartTime=runningStartTime;

    }


    public float getCalorie() {
        return calorie;
    }

    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }

    public int getRunningSteps() {
        return runningSteps;
    }

    public void setRunningSteps(int runningSteps) {
        this.runningSteps = runningSteps;
    }

    public String getRunningTrail() {
        return runningTrail;
    }

    public void setRunningTrail(String runningTrail) {
        this.runningTrail = runningTrail;
    }

    public double getRunningDistance() {
        return runningDistance;
    }

    public void setRunningDistance(double runningDistance) {
        this.runningDistance = runningDistance;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getRunningLocation() {
        return runningLocation;
    }

    public void setRunningLocation(String runningLocation) {
        this.runningLocation = runningLocation;
    }

    public String getRunningStartTime() {
        return runningStartTime;
    }

    public void setRunningStartTime(String runningStartTime) {
        this.runningStartTime = runningStartTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
