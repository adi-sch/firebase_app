package com.database.firebase_app;

import static com.database.firebase_app.FBRef.refAuth;
import static com.database.firebase_app.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoIOActivity extends AppCompatActivity
{

    private EditText eTTitle, eTAuthor, eTRating, eTHaveRead;
    private TextView tVMsg;
    private ListView lVBooks;

    ValueEventListener booksListener;
    private DatabaseReference refCurrUserBooks;
    private ArrayList<Book> booksList;
    private CustomAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_ioactivity);

        /*String userID = this.getIntent().getStringExtra("userID");
        refCurrUserBooks = refUsers.child(userID).child("Books");*/
        refCurrUserBooks = refUsers.child(refAuth.getUid()).child("Books");

        init();
        updateBooksListView();
    }

    private void init()
    {
        eTTitle = findViewById(R.id.eTTitle);
        eTAuthor = findViewById(R.id.eTAuthor);
        eTRating = findViewById(R.id.eTRating);
        eTHaveRead = findViewById(R.id.eTHaveRead);
        tVMsg = findViewById(R.id.tVMsg);
        lVBooks = findViewById(R.id.lVBooks);

        booksList = new ArrayList<>();
        adp = new CustomAdapter(this, booksList);
        lVBooks.setAdapter(adp);
    }

    public void addBook(View view)
    {
        String title = eTTitle.getText().toString();
        String author = eTAuthor.getText().toString();
        int rating = Integer.parseInt(eTRating.getText().toString());
        String haveReadStr = eTHaveRead.getText().toString();
        if (title.isEmpty() || author.isEmpty() || rating<0 || rating>5 || haveReadStr.isEmpty())
        {
            tVMsg.setText("Please fill all fields");
            return;
        }
        if (!haveReadStr.equals("Y") && !haveReadStr.equals("N"))
        {
            tVMsg.setText("Please enter in have read field Y or N");
            return;
        }
        Boolean haveRead = haveReadStr.equals("Y");

        String bookId = refCurrUserBooks.push().getKey();
        Book newBook = new Book(bookId, title, author, rating, haveRead);
        refCurrUserBooks.child(bookId).setValue(newBook);

        /*booksList.add(newBook);
        updateBooksListView();*/
    }

    public void updateBooksListView()
    {
        booksListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                booksList.clear();
                for (DataSnapshot data: snapshot.getChildren())
                {
                    Book bookTemp = data.getValue(Book.class);
                    booksList.add(bookTemp);
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        refCurrUserBooks.addValueEventListener(booksListener);
    }

    @Override
    protected void onPause()
    {
        if (booksListener!=null)
        {
            refCurrUserBooks.removeEventListener(booksListener);
        }
        super.onPause();
    }

    public void sortAndFilterActivity(View view)
    {
        Intent intent = new Intent(this, SortAndFilterActivity.class);
        startActivity(intent);
    }
}