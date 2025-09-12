package com.database.firebase_app;

public class User
{
    private String userID;
    private String name;
    private String age;
    private String address;

    public User(String userID, String name, String age, String address)
    {
        this.address = address;
        this.age = age;
        this.name = name;
        this.userID = userID;
    }

    public User() {}

    public String getUserID()
    {
        return this.userID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAge()
    {
        return this.age;
    }

    public String getAddress()
    {
        return this.address;
    }
}
