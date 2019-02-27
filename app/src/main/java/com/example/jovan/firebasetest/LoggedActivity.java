package com.example.jovan.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoggedActivity extends AppCompatActivity {

    Button btnSignOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        initUI();
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = mAuth.getCurrentUser().getEmail();
                    Toast.makeText(LoggedActivity.this, name, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(LoggedActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                mAuth.signOut();
                Intent i = new Intent(LoggedActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUI() {
        btnSignOut = findViewById(R.id.btnSignOut);
    }
}
