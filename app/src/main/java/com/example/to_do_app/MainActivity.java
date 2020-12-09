package com.example.to_do_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    LinearLayout login;
    TextView signUp;

    String username, password;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        user = findViewById(R.id.editTextUsername);
        pass = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.textViewSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                ref = FirebaseDatabase.getInstance().getReference("user");
                Query q = ref.orderByChild("username").equalTo(username);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String dataPass = snapshot.child(username).child("password").getValue().toString();
                            if(dataPass.equals(password)){
                                Intent intent = new Intent(v.getContext(), mainPage.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }else{
                                pass.setError("Wrong password");
                            }
                        }else{
                            user.setError("No username found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), signUpPage.class);
                startActivity(intent);
            }
        });
    }
}