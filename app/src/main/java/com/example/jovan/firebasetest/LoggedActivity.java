package com.example.jovan.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoggedActivity extends AppCompatActivity {

    Button btnSignOut;
    FirebaseAuth mAuth;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users");
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        initUI();
        init();
        readFromDatabase();
    }


    public void readFromDatabase(){
        final String uId = mAuth.getCurrentUser().getUid();

        final DatabaseReference userRef = dbRef.child(uId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{

                    for(DataSnapshot post : dataSnapshot.getChildren()){
                        HashMap<String, String> map = (HashMap<String, String>) post.getValue();
                        User user = new User(map.get("firstName"), map.get("lastName"), map.get("email"));
                        txtView.setText(String.format("First name: %s\n Last name: %s\n E-mail: %s\n"
                                ,user.firstName, user.lastName, user.email));
                    }
                }catch (Exception e){
                    Toast.makeText(LoggedActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = mAuth.getCurrentUser().getEmail();
                    String uID = mAuth.getCurrentUser().getUid();
                    DatabaseReference userRef = dbRef.child(uID);


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
        txtView = findViewById(R.id.txtView);
    }
}
