package com.database.firebase_app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;

public class InfoIOActivity extends AppCompatActivity {

    private EditText eTTitle, eTAuthor, eTRating, eTHaveRead;
    private TextView tVMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_ioactivity);

        init();
    }

    private void init()
    {
        eTTitle = findViewById(R.id.eTTitle);
        eTAuthor = findViewById(R.id.eTAuthor);
        eTRating = findViewById(R.id.eTRating);
        eTHaveRead = findViewById(R.id.eTHaveRead);
        tVMsg = findViewById(R.id.tVMsg);
    }

    public void addBook(View view)
    {
        String title = eTTitle.getText().toString();
        String author = eTAuthor.getText().toString();
        int rating = Integer.parseInt(eTRating.getText().toString());
        boolean haveRead = eTHaveRead.getText().toString();
        if (title.isEmpty() || author.isEmpty() || name.isEmpty() || age.isEmpty() || addr.isEmpty())
        {
            tVMsg.setText("Please fill all fields");
        }
    }
}