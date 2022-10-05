package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TimeTableAdminActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    private List<String> cl;
    //data
    private Spinner sp;
    //buttons
    private ImageView addt;
    private ImageView removet;
    private ImageView editt;
    //database
    private FirebaseDatabase fref;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_admin);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //profile
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        name = findViewById(R.id.user_name);
        pfp = findViewById(R.id.profile_photo);
        name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pfp);
        today = Calendar.getInstance().getTime();//getting date
        formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        date = formatter.format(today);
        tv1 = findViewById(R.id.date);
        tv1.setText(date);

        //data
        sp = findViewById(R.id.classes);

        fref = FirebaseDatabase.getInstance();
        ref = fref.getReference("Class");

        addt = findViewById(R.id.addtimet);
        removet = findViewById(R.id.removetimet);
        editt = findViewById(R.id.edittimet);
        addt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp.getSelectedItem().toString().equals("Class")) {
                    Intent timetabler = new Intent(TimeTableAdminActivity.this, TimeTableAddActivity.class);
                    startActivity(timetabler);
                }
                else
                {
                    Intent timetabler = new Intent(TimeTableAdminActivity.this,TimeTableAddFActivity.class);
                    startActivity(timetabler);
                }

            }
        });
        removet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sp.getSelectedItem().toString().equals("Class")) {
                    Intent remover = new Intent(TimeTableAdminActivity.this,TimeTableRemoveActivity.class);
                    startActivity(remover);
                }
                else
                {
                    Intent remover = new Intent(TimeTableAdminActivity.this,TimeTableRemoveFActivity.class);
                    startActivity(remover);
                }

            }
        });



    }
}