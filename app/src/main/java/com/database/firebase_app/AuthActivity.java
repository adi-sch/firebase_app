package com.database.firebase_app;

import static com.database.firebase_app.FBRef.refAuth;
import static com.database.firebase_app.FBRef.refUsers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity
{
    private EditText eTEmail, eTPass, eTName, eTAge, eTAddr;
    private TextView tVMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        init();
    }

    private void init()
    {
        eTEmail = findViewById(R.id.eTEmail);
        eTPass = findViewById(R.id.eTPass);
        eTName = findViewById(R.id.eTName);
        eTAge = findViewById(R.id.eTAge);
        eTAddr = findViewById(R.id.eTAddr);
        tVMsg = findViewById(R.id.tVMsg);

        enableSignupFields(false);
    }

    private void enableSignupFields(boolean state)
    {
        eTName.setEnabled(state);
        eTAge.setEnabled(state);
        eTAddr.setEnabled(state);
    }

    public void signUp(View view)
    {
        enableSignupFields(true);
        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();
        String name = eTName.getText().toString();
        String age = eTAge.getText().toString();
        String addr = eTAddr.getText().toString();
        if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || age.isEmpty() || addr.isEmpty())
        {
            tVMsg.setText("Please fill all fields");
        }
        else
        {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Creating user...");
            pd.show();
            refAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            pd.dismiss();
                            if (task.isSuccessful())
                            {
                                Log.i("AuthActivity", "createUserWithEmailAndPassword:success");
                                FirebaseUser authUser = refAuth.getCurrentUser();
                                User dbUser = new User(authUser.getUid(), name, age, addr);
                                refUsers.child(dbUser.getUserID()).setValue(dbUser);

                                authEndedSuccessfully(authUser.getUid());
                            }
                            else
                            {
                                authFailed(task.getException());
                                enableSignupFields(false);
                            }
                        }
                    });
        }
    }

    public void login(View view)
    {
        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();
        if (email.isEmpty() || pass.isEmpty())
        {
            tVMsg.setText("Please fill all fields");
        }
        else
        {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Logging in user...");
            pd.show();
            refAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            pd.dismiss();
                            if (task.isSuccessful())
                            {
                                Log.i("AuthActivity", "loginUserWithEmailAndPassword:success");
                                FirebaseUser authUser = refAuth.getCurrentUser();

                                authEndedSuccessfully(authUser.getUid());
                            }
                            else
                            {
                                authFailed(task.getException());
                            }
                        }
                    });
        }
    }

    private void authEndedSuccessfully(String userID)
    {
        tVMsg.setText("User created successfully\nUid: " + userID);

        Intent intent = new Intent(this, InfoIOActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    private void authFailed(Exception exp)
    {
        if (exp instanceof FirebaseAuthInvalidUserException){
            tVMsg.setText("Invalid email address.");
        } else if (exp instanceof FirebaseAuthWeakPasswordException) {
            tVMsg.setText("Password too weak.");
        } else if (exp instanceof FirebaseAuthUserCollisionException) {
            tVMsg.setText("User already exists.");
        } else if (exp instanceof FirebaseAuthInvalidCredentialsException) {
            tVMsg.setText("General authentication failure.");
        } else if (exp instanceof FirebaseNetworkException) {
            tVMsg.setText("Network error. Please check your connection and try again.");
        } else {
            tVMsg.setText("An error occurred. Please try again later.");
        }
    }
}