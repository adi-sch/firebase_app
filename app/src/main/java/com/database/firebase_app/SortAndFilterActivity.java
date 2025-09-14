package com.database.firebase_app;

import static com.database.firebase_app.FBRef.refAuth;
import static com.database.firebase_app.FBRef.refUsers;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SortAndFilterActivity extends AppCompatActivity //implements AdapterView.OnItemSelectedListener
{
    private EditText eTFTitle, eTFAuthor, eTFRatingFrom, eTFRatingTo, eTFHaveRead;
    private TextView tVMsg;
    private ListView lVBooks;
    private DatabaseReference refCurrUserBooks;

    /*private String keys[];
    private ArrayAdapter<String> adpKeys;
    private Spinner spinnerSort;
    private String keyChose;*/

    private ArrayList<Book> booksList;
    private CustomAdapter adpBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sort_and_filter);

        refCurrUserBooks = refUsers.child(refAuth.getUid()).child("Books");
        init();
    }

    private void init()
    {
        eTFTitle = findViewById(R.id.eTFTitle);
        eTFAuthor = findViewById(R.id.eTFAuthor);
        eTFRatingFrom = findViewById(R.id.eTFRatingFrom);
        eTFRatingTo = findViewById(R.id.eTFRatingTo);
        eTFHaveRead = findViewById(R.id.eTFHaveRead);
        tVMsg = findViewById(R.id.tVMsg);
        lVBooks = findViewById(R.id.lVBooks);
        //spinnerSort = findViewById(R.id.spinnerSort);

        booksList = new ArrayList<>();
        adpBooks = new CustomAdapter(this, booksList);
        lVBooks.setAdapter(adpBooks);

        /*spinnerSort.setOnItemSelectedListener(this);

        keys = getResources().getStringArray(R.array.sortKeys);
        adpKeys = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, keys);
        spinnerSort.setAdapter(adpKeys);
        keyChose = keys[0];*/
    }

    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        keyChose = keys[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}*/

    public void search(View view)
    {
        boolean filter = false;
        Query query = refCurrUserBooks;

        String fTitle = eTFTitle.getText().toString();
        String fAuthor = eTFAuthor.getText().toString();
        String fRatingFromStr = eTFRatingFrom.getText().toString();
        String fRatingToStr = eTFRatingTo.getText().toString();
        String fHaveReadStr = eTFHaveRead.getText().toString();

        //only works with one orderBy
        if (fTitle.length() != 0)
        {
            query = query.orderByChild("title").equalTo(fTitle);
            filter = true;
        }
        if (fAuthor.length() != 0)
        {
            query = query.orderByChild("author").equalTo(fAuthor);
            filter = true;
        }
        if (fRatingFromStr.length() !=0 && fRatingToStr.length() != 0)
        {
            query = query.orderByChild("rating").startAt(Integer.parseInt(fRatingFromStr))
                    .endAt(Integer.parseInt(fRatingToStr));
            filter = true;
        }
        if (fHaveReadStr.equals("Y") || fHaveReadStr.equals("N"))
        {
            query = query.orderByChild("haveRead").equalTo(fHaveReadStr.equals("Y") ? true : false);
            filter = true;
        }

        if (filter == true)
        {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    booksList.clear();
                    for (DataSnapshot data: snapshot.getChildren())
                    {
                        Book bookTemp = data.getValue(Book.class);
                        booksList.add(bookTemp);
                    }
                    adpBooks.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}