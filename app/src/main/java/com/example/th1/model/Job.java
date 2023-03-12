package com.example.th1.model;

import java.util.Date;

public class Job {

    private String name;
    private String des;
    private String fors;
    private Date date;

    public Job() {
    }

    public Job(String name, String des, String fors, Date date) {
        this.name = name;
        this.des = des;
        this.fors = fors;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", fors='" + fors + '\'' +
                ", date=" + date +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getFors() {
        return fors;
    }

    public void setFors(String fors) {
        this.fors = fors;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
