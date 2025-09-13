package com.database.firebase_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Book> booksList;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Book> booksList)
    {
        this.context = context;
        this.booksList = booksList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return booksList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent)
    {
        view = inflater.inflate(R.layout.custom_lv_books_layout, parent, false);
        TextView clvTitle = view.findViewById(R.id.clvTitle);
        TextView clvRating = view.findViewById(R.id.clvRating);
        TextView clvAuthor = view.findViewById(R.id.clvAuthor);
        TextView clvHaveRead = view.findViewById(R.id.clvHaveRead);

        clvTitle.setText(booksList.get(i).getTitle());
        clvRating.setText(Integer.toString(booksList.get(i).getRating()));
        clvAuthor.setText(booksList.get(i).getAuthor());
        clvHaveRead.setText(booksList.get(i).isHaveRead() ? "Read" : "didn't read");

        return view;
    }
}
