package com.popoaichuiniu.jacy.Data;

/**
 * Created by jacy on 2015/12/5.
 */
public class PlanData {

     private String label="";
     private String time="";
    private String repeat="只响一次";
    private String vibrate="false";
    private String music="xxxxxxxxxx";
    private String whetherRun="true";
    private int planID;

    public PlanData(String label, String music, String repeat, String time, String vibrate, String whetherRun,int planID) {
        this.label = label;
        this.music = music;
        this.repeat = repeat;
        this.time = time;
        this.vibrate = vibrate;
        this.whetherRun = whetherRun;
        this.planID=planID;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVibrate() {
        return vibrate;
    }

    public void setVibrate(String vibrate) {
        this.vibrate = vibrate;
    }

    public String getWhetherRun() {
        return whetherRun;
    }

    public void setWhetherRun(String whetherRun) {
        this.whetherRun = whetherRun;
    }
}
