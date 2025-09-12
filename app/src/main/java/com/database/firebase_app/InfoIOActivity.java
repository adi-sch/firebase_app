package com.database.firebase_app;

import static com.database.firebase_app.FBRef.refUsers;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class InfoIOActivity extends AppCompatActivity {

    private EditText eTTitle, eTAuthor, eTRating, eTHaveRead;
    private TextView tVMsg;
    private ListView lVBooks;
    private DatabaseReference refCurrUserBooks;
    private ArrayList<Book> booksList;
    private ArrayAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_ioactivity);

        String userID = this.getIntent().getStringExtra("userID");
        refCurrUserBooks = refUsers.child(userID).child("Books");
        init();
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
        adp = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, booksList);
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

        booksList.add(newBook);
        updateBooksListView();
    }

    public void updateBooksListView()
    {
        adp.notifyDataSetChanged();
    }
}