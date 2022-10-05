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

public class TopbarActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView name;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbar2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Firebase Instance
        mAuth = FirebaseAuth.getInstance();

        //user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //ids
        name = findViewById(R.id.user_name);
        pfp = findViewById(R.id.profile_photo);

        //setting name
        name.setText(currentUser.getDisplayName());

        //setting image
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pfp);

        //date
        today = Calendar.getInstance().getTime();//getting date
        formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to need
        date = formatter.format(today);
        tv1 = findViewById(R.id.date);
        tv1.setText(date);
    }
    public void gosettings(View view)
    {
        Intent settings = new Intent(this,SettingsActivity.class);
        startActivity(settings);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}