package com.database.firebase_app;

public class User
{
    private String userID;
    private String name;
    private String age;
    private String address;

    public User(String address, String age, String name, String userID)
    {
        this.address = address;
        this.age = age;
        this.name = name;
        this.userID = userID;
    }

    public User() {}
}
