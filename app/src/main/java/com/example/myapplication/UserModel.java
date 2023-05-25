package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    int id;
    @SerializedName("email")
    String email;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("phone")
    String phone;

    public static class Name {
        @SerializedName("firstname")
        String firstname;

        @SerializedName("lastname")
        String lastname;

        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
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
    }

    @SerializedName("name")
    Name name;

    public static class Address {
        @SerializedName("city")
        String city;
        @SerializedName("street")
        String street;
        @SerializedName("number")
        int number;
        @SerializedName("zipcode")
        String zipcode;

        public Address(String city, String street, int number, String zipcode) {
            this.city = city;
            this.street = street;
            this.number = number;
            this.zipcode = zipcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }

    @SerializedName("address")
    Address address;

    public UserModel(int id, String email, String username, String password, String phone, Name name, Address address) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
