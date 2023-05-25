package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterModel implements Serializable {
    String firstname, lastname, birthday, age, email, phone, address, province, postcode, username, password, imageURI, title, university;
    ArrayList<String> hobbyList;

    boolean gender;
    public RegisterModel(String firstname, String lastname, String birthday, String age, String email, String phone, String address, String province, String postcode, String username, String password, String imageURI, boolean gender, String title, ArrayList<String> hobbyList, String university) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.province = province;
        this.postcode = postcode;
        this.username = username;
        this.password = password;
        this.imageURI = imageURI;
        this.gender = gender;
        this.title = title;
        this.hobbyList = hobbyList;
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public boolean isGender() {
        return gender;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(ArrayList<String> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
