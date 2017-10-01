package com.example.count;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yidingfan on 2017-09-29.
 */

/**
 * This class is the counter book
 * with name,init,value and comment
 */
public class counter {
    private String name;
    private Date date;
    private int init;
    private int value;
    private String comment;

    /**
     * constructor
     * @param Name the name of book
     * @param n initial value
     * @param comment   comments for book
     */
    public counter(String Name,int n,String comment) {
        this.name = Name;
        this.date = Calendar.getInstance().getTime();
        this.comment = comment;
        this.init = n;
        this.value = n;
    }

    /**
     * increase current value by 1
     */
    public void increase(){
        this.date = Calendar.getInstance().getTime();
        this.value=this.value+1;
    }

    /**
     * decrease current value by 1
     */
    public void decrease(){
        if (this.value>0) {
            this.date = Calendar.getInstance().getTime();

            this.value = this.value - 1;
        }
    }

    /**
     * set the initial value to N
     * @param N
     */
    public void setInit(int N){
        this.date = Calendar.getInstance().getTime();
        this.init=N;
    }

    /**
     * set the name to N
     * @param N
     */
    public void setName(String N){
        this.date = Calendar.getInstance().getTime();
        this.name=N;
    }

    /**
     * set the comment to N
     * @param N
     */
    public void setComment( String N){
        this.date = Calendar.getInstance().getTime();
        this.comment=N;
    }

    /**
     * set the current value to N
     * @param N
     */
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
