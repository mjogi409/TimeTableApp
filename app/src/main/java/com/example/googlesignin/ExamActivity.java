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

public class ExamActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
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
    }
    public void gotcie1(View view)
    {
        Intent tcie1 = new Intent(this,TCIE1Activity.class);
        startActivity(tcie1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void gosettings(View view)
    {
        Intent settings = new Intent(this,SettingsActivity.class);
        startActivity(settings);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void gotcie2(View view)
    {
        Intent tcie2 = new Intent(this,TCIE2Activity.class);
        startActivity(tcie2);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void gosee(View view)
    {
        Intent see = new Intent(this,SEEActivity.class);
        startActivity(see);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}