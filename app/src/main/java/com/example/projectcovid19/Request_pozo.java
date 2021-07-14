package com.example.projectcovid19;

import java.util.Date;

public class Request_pozo {

    public String username;
    public String request;
    public String finaldate;
    public String phoneno;
    public String City;
    public String citypincode;
    public String user_id;



    public Request_pozo() {
    }

    public Request_pozo(String username, String request, String finaldate, String phoneno, String city, String citypincode) {

        this.username = username;
        this.request = request;
        this.finaldate = finaldate;
        this.phoneno = phoneno;
        this.City = city;
        this.citypincode = citypincode;
        this.user_id = user_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
        return finaldate;
    }

    public void setFinaldate(String finaldate) {
        this.finaldate = finaldate;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCitypincode() {
        return citypincode;
    }

    public void setCitypincode(String citypincode) {
        this.citypincode = citypincode;
    }


}
