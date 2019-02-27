package com.example.jovan.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnOk;
    private User user;
    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText password;
    private FirebaseDatabase fDb;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        init();
        user = new User();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String firstName = fname.getText().toString();
        String lastName = lname.getText().toString();
        String mail = email.getText().toString();
        user = new User(firstName, lastName, mail);
        final String pasw = password.getText().toString();




        mAuth.createUserWithEmailAndPassword(mail, pasw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // storing the information for user
                            String firstName = fname.getText().toString();
                            String lastName = lname.getText().toString();
                            String mail = email.getText().toString();
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            user = new User(firstName, lastName, mail);
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = db.getReference();
                            try{
                                dbRef.child("users").child(uId).push().setValue(user);
                            }catch (Exception e){
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }finally {
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                i.putExtra("email", mail);
                                i.putExtra("password", pasw);
                                startActivity(i);
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } 
                });



    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            // handle the current logged in user
        }
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        fDb = FirebaseDatabase.getInstance();
    }

    private void initUI(){
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }


    private void cancel(){
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
    }
}
