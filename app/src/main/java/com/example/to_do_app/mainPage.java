package com.example.to_do_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainPage extends AppCompatActivity {
    ImageView add;
    ImageView logout;
    TextView user;
    DatabaseReference ref;
    ListView list;
    ArrayList<String> title;
    ArrayList<String> desc;
    ArrayList<String> date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        String myUser = getIntent().getStringExtra("username");

        add = findViewById(R.id.imageViewAdd);
        logout = findViewById(R.id.imageViewLogOut);
        user = findViewById(R.id.textViewUser);
        list = findViewById(R.id.listView);
        title = new ArrayList<>();
        desc = new ArrayList<>();
        date = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("to_do_list")
                .orderByChild("username")
                .equalTo(myUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    title.add(snapshot1.child("title").getValue().toString());
                    desc.add(snapshot1.child("describe").getValue().toString());
                    date.add(snapshot1.child("date").getValue().toString());
                }
                customAdapter custom = new customAdapter();
                list.setAdapter(custom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addToDo.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Toast.makeText(mainPage.this, "Loged out", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
    class customAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return title.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.single_item, null);

            TextView myTitle =(TextView) view1.findViewById(R.id.textViewTitle);
            TextView myDesc =(TextView) view1.findViewById(R.id.textViewDescribe);
            TextView myDate =(TextView) view1.findViewById(R.id.textViewDate);

            myTitle.setText(title.get(position));
            myDesc.setText(desc.get(position));
            myDate.setText(date.get(position));
            return view1;
        }
    }
}

