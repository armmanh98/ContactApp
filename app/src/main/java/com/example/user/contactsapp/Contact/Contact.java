package com.example.user.contactsapp.Contact;

import java.io.Serializable;

/**
 * Created by User on 7/12/2017.
 */

public class Contact implements Serializable {
    private String name;
    private int number;
    private int age;
    private String gender;

    public Contact(String name, int number, int age, String gender) {
        this.name = name;
        this.number = number;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
