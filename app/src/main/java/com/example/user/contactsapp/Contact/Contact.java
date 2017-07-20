package com.example.user.contactsapp.Contact;

/**
 * Created by User on 7/12/2017.
 */

public class Contact  {
    private int id;
    private String name;
    private String number;
    private String age;
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Contact(){

    }

    public Contact(int id, String name, String number, String age, String gender) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.age = age;
        this.gender = gender;
    }
    public Contact(String name, String number, String age, String gender) {

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
