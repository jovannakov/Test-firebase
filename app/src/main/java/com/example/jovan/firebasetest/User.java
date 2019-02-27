package com.example.jovan.firebasetest;


import java.io.Serializable;

public class User implements Serializable {
    public String email;
    public String firstName;
    public String lastName;

    public User(){
        this.firstName = "";
        this.lastName = "";
        this.email = "";
       // this.password = "";
    }

    public User(String firstName, String lastName,String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        //this.password = password;
    }

    @Override
    public String toString() {
        return this.lastName + this.firstName.substring(0,1);
    }



}
