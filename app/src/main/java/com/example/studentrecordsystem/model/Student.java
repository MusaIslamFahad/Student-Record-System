package com.example.studentrecordsystem.model;

public class Student {
    private int id;
    private String course;
    private long date;
    private String studentname;
    private String gender;
    private String physically;
    private String session;
    private String guardian;
    private String nationality;
    private String mobile;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;

    private String hscboard;
    private String hsccgpa;


    private String sscboard;
    private String ssccgpa;


    public Student(int id, String course, long date, String studentname, String gender, String physically, String session, String guardian, String nationality, String mobile, String email, String address, String city, String state, String country, String hscboard, String hsccgpa, String sscboard, String ssccgpa) {
        this.id = id;
        this.course = course;
        this.date = date;
        this.studentname = studentname;
        this.gender = gender;
        this.physically = physically;
        this.session = session;
        this.guardian = guardian;
        this.nationality = nationality;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.hscboard = hscboard;
        this.hsccgpa = hsccgpa;
        this.sscboard = sscboard;
        this.ssccgpa = ssccgpa;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhysically() {
        return physically;
    }

    public void setPhysically(String physically) {
        this.physically = physically;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHscboard() {
        return hscboard;
    }

    public void setHscboard(String hscboard) {
        this.hscboard = hscboard;
    }

    public String getHsccgpa() {
        return hsccgpa;
    }

    public void setHsccgpa(String hsccgpa) {
        this.hsccgpa = hsccgpa;
    }

    public String getSscboard() {
        return sscboard;
    }

    public void setSscboard(String sscboard) {
        this.sscboard = sscboard;
    }

    public String getSsccgpa() {
        return ssccgpa;
    }

    public void setSsccgpa(String ssccgpa) {
        this.ssccgpa = ssccgpa;
    }

}

