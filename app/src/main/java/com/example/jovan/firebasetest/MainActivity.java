package com.example.jovan.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    Button btn;
    Button signIn;
    EditText email;
    EditText password;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        init();

        Bundle extras = getIntent().getExtras();
        showData(extras);

    }

    private void init() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
                /*Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);*/
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void initUI() {
        email = findViewById(R.id.emailTxt);
        password = findViewById(R.id.passwordTxt);
        btn = findViewById(R.id.btnRegister);
        signIn = findViewById(R.id.btnSign);
    }

    private void showData(Bundle extras) {
        if(extras != null){
            email.setText(extras.getString("email"));
            password.setText(extras.getString("password"));
        }
    }

    private void SignIn() {
        String em = email.getText().toString();
        String pasw = password.getText().toString();

        mAuth.signInWithEmailAndPassword(em, pasw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            try{
                                user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "You signed in succesfull!"
                                        , Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }finally {
                                Intent i = new Intent(MainActivity.this, LoggedActivity.class);
                                startActivity(i);
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Problem with your credentials"
                            , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SignUp(){
        String em = email.getText().toString();
        String pasw = password.getText().toString();

        try{
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        /*mAuth.createUserWithEmailAndPassword(em, pasw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Registration went great!"
                                    , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Problem with the registration"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

    }
}
