package com.database.firebase_app;

import com.google.firebase.Timestamp;

public class Book
{
    private String bookID;
    private String title;
    private String author;
    private int rating;
    private boolean haveRead;

    public Book(String bookId, String title, String author, int rating, boolean haveRead)
    {
        this.bookID = bookID; //- in my project i would need to get the key
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.haveRead = haveRead;
    }

    public void setBookID(String bookID)
    {
        this.bookID = bookID;
    }

    public String getBookID()
    {
        return this.bookID;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public int getRating()
    {
        return this.rating;
    }

    public boolean isHaveRead()
    {
        return this.haveRead;
    }
}
