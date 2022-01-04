package com.example.projectcovid19;

import java.util.Date;

public class Request_pozo {
    public String username;
    public String request;
    public String date;
    public String phoneno;

    public String pincode;



    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }



    public Request_pozo(String username, String request, String date, String phoneno) {
        this.username = username;
        this.request = request;
        this.date = date;
        this.phoneno = phoneno;

    }
    public Request_pozo() {
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

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }










}
