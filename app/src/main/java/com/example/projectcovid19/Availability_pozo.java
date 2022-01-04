package com.example.projectcovid19;

public class Availability_pozo {


    public String username;
    public String request;
    public String date;
    public String PhoneNumber;
    public String City;

    public Availability_pozo(String username, String request, String date, String PhoneNumber) {
        this.username = username;
        this.request = request;
        this.date = date;
        this.PhoneNumber = PhoneNumber;

    }
    public Availability_pozo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getFinaldate() {
        return date;
    }

    public void setFinaldate(String finaldate) {
        this.date = finaldate;
    }

    public String getPhoneno() {
        return PhoneNumber;
    }

    public void setPhoneno(String phoneno) {
        this.PhoneNumber = phoneno;
    }

}
