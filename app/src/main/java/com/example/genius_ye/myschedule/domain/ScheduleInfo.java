package com.example.genius_ye.myschedule.domain;


/*
定义日程的实体类
 */
public class ScheduleInfo {
    private String day;
    private String name;
    private String plan;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "ScheduleInfo{" +
                "day='" + day + '\'' +
                ", name='" + name + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
