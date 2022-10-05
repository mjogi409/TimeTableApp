package com.example.googlesignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DashBoardAdminActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    private ImageView adduser;
    private ImageView removeuser;
    private ImageView updateuser;
    private ImageView timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_admin);
        Objects.requireNonNull(getSupportActionBar()).hide();
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
        adduser = findViewById(R.id.imageView15);
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adduser = new Intent(DashBoardAdminActivity.this, AddUserActivity.class);
                startActivity(adduser);
            }
        });
        removeuser = findViewById(R.id.removeuser);
        removeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent remove = new Intent(DashBoardAdminActivity.this, RemoveUserActivity.class);
                startActivity(remove);
            }
        });
        updateuser = findViewById(R.id.updateuser_btn);
        updateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DashBoardAdminActivity.this, UpdateUserActivity.class);
                startActivity(update);
            }
        });
        timetable = findViewById(R.id.timetable_btn);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent timet = new Intent(DashBoardAdminActivity.this,TimeTableAdminActivity.class);
                startActivity(timet);
            }
        });
    }

    public void goadduser(View view) {
        Intent adduser = new Intent(DashBoardAdminActivity.this, AddUserActivity.class);
        startActivity(adduser);
    }

}