package com.example.to_do_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpPage extends AppCompatActivity {
    EditText user;
    EditText pass;
    EditText conPass;
    LinearLayout login;
    LinearLayout back;

    String myUser, myPass, myCon;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();

        user = findViewById(R.id.editTextCreateUser);
        pass = findViewById(R.id.editTextCreatePass);
        conPass = findViewById(R.id.editTextCreateCon);
        login = findViewById(R.id.btnLogin);
        back = findViewById(R.id.btnBack);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                myPass = pass.getText().toString();
                myCon = conPass.getText().toString();
                if(myPass.equals(myCon)){
                    ref = FirebaseDatabase.getInstance().getReference("user");
                    userDetail d1 = new userDetail(myUser, myPass);
                    ref.child(myUser).setValue(d1);
                    Toast.makeText(signUpPage.this, "Successful Created", Toast.LENGTH_LONG).show();
                    goBack(v);
                }else{
                    conPass.setError("Password not match");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
}