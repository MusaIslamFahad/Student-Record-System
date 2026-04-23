package com.example.studentrecordsystem.model;

public class Course{
    private int id;
    private String fname;
    private String sname;
    private long date;


    public Course(int id, String fname, String sname, long date) {
        this.id = id;
        this.fname = fname;
        this.sname = sname;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setfname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setsname(String sname) {
        this.sname = sname;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
