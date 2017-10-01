package com.example.count;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yidingfan on 2017-09-29.
 */

public class counter {
    private String name;
    private Date date;
    private int init;
    private int value;
    private String comment;
    public counter(String Name,int n,String comment) {
        this.name = Name;
        this.date = Calendar.getInstance().getTime();
        this.comment = comment;
        this.init = n;
        this.value = n;
    }
    public void increase(){
        this.date = Calendar.getInstance().getTime();
        this.value=this.value+1;
    }
    public void decrease(){
        if (this.value>0) {
            this.date = Calendar.getInstance().getTime();

            this.value = this.value - 1;
        }
    }
    public void setInit(int N){
        this.date = Calendar.getInstance().getTime();
        this.init=N;
    }
    public void setName(String N){
        this.date = Calendar.getInstance().getTime();
        this.name=N;
    }
    public void setComment( String N){
        this.date = Calendar.getInstance().getTime();
        this.comment=N;
    }
    public void setValue(int N){
        this.date = Calendar.getInstance().getTime();
        this.value=N;
    }
    public String getName(){
        return this.name;
    }
    public String getComment(){
        return this.comment;
    }
    public int getInit(){
        return this.init;
    }
    public int getCurr(){
        return this.value;
    }
    public String toString() {
        return "Name: "+this.name+" \ninitial: "+this.init+" \ncurrent value: "+this.value+" \nlast modified: "+this.date
                +" \ncomment: "+ this.comment;
    }
}
