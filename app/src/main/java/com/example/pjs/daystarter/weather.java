package com.example.pjs.daystarter;

/**
 * Created by PJS on 2017-12-17.
 */

public class weather {
    private String hour;
    private String wfEn;
    private String wfKor;
    private String temp;
    private String pop;

    public String getWfKor() {
        return wfKor;
    }

    public void setWfKor(String wfKor) {
        this.wfKor = wfKor;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWfEn() {
        return wfEn;
    }

    public void setWfEn(String wfEn) {
        this.wfEn = wfEn;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }
}
