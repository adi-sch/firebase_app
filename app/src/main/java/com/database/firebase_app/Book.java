package com.database.firebase_app;

import com.google.firebase.Timestamp;

public class Book
{
    private String bookID;
    private String title;
    private String author;
    private int rating;
    private Timestamp dateRead;

    public Book(String bookID, String title, String author, int rating, Timestamp dateRead)
    {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.dateRead = dateRead;
    }
}
