package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class UniversityModel {
    @SerializedName("country")
    private String country;

    @SerializedName("name")
    private String name;

    @SerializedName("state-province")
    private String state_province;

    public UniversityModel(String country, String name, String state_province) {
        this.country = country;
        this.name = name;
        this.state_province = state_province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }
}
