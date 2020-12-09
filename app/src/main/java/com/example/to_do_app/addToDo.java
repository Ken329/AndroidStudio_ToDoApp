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

public class addToDo extends AppCompatActivity {
    EditText title;
    EditText desc;
    EditText date;
    LinearLayout back;
    LinearLayout enter;
    DatabaseReference ref;
    String myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        getSupportActionBar().hide();

        myUser = getIntent().getStringExtra("username");

        title = findViewById(R.id.editTextTitle);
        desc = findViewById(R.id.editTextDesc);
        date = findViewById(R.id.editTextDate);
        back = findViewById(R.id.btnBack);
        enter = findViewById(R.id.btnEnter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTitle = title.getText().toString();
                String myDesc = desc.getText().toString();
                String myDate = date.getText().toString();
                if (myTitle == "" || myDesc == "" || myDate == ""){
                    Toast.makeText(addToDo.this, "Do not leave anything empty", Toast.LENGTH_LONG).show();
                }else{
                    detail d1 = new detail(myTitle, myDesc, myDate, myUser);
                    ref = FirebaseDatabase.getInstance().getReference("to_do_list");
                    ref.child(myTitle).setValue(d1);
                    Toast.makeText(addToDo.this, "New Note Stored", Toast.LENGTH_LONG).show();
                    goBack(v);
                }
            }
        });
    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), mainPage.class);
        intent.putExtra("username", myUser);
        startActivity(intent);
    }
}