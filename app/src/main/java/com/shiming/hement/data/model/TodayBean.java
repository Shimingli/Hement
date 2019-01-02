package com.shiming.hement.data.model;


import com.shiming.network.entities.BaseOneBean;


/**
 * Created by shiming on 2017/4/4.
 */
public class TodayBean extends BaseOneBean {

    /**
     * day : 1/1
     * date : 前45年01月01日
     * title : 罗马共和国开始使用儒略历
     * e_id : 1
     */

    public String day;
    public String date;
    public String title;
    public String e_id;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    @Override
    public String[] uniqueKey() {
        return new String[0];
    }
}
