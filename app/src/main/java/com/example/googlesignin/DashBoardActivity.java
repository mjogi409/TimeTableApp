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

public class DashBoardActivity extends AppCompatActivity {
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
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dash_board);
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

    public void viewtt(View view)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if(dayOfTheWeek.equals("Monday")) {
            Intent i = new Intent(DashBoardActivity.this, MonActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else if(dayOfTheWeek.equals("Tuesday"))
        {
            Intent i = new Intent(DashBoardActivity.this, TueActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else if(dayOfTheWeek.equals("Thursday"))
        {
            Intent i = new Intent(DashBoardActivity.this, ThurActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else if(dayOfTheWeek.equals("Wednesday"))
        {
            Intent i = new Intent(DashBoardActivity.this, WedActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else if(dayOfTheWeek.equals("Friday")){
            Intent i = new Intent(DashBoardActivity.this, FriActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else
        {
            Intent i = new Intent(DashBoardActivity.this, MonActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }

    }
    public void gosettings(View view)
    {
        Intent settings = new Intent(DashBoardActivity.this,SettingsActivity.class);
        startActivity(settings);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void goexam(View view)
    {
        Intent exam = new Intent(this,ExamActivity.class);
        startActivity(exam);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void gonotification(View view)
    {
        Intent notification = new Intent(this,NotificationActivity.class);
        startActivity(notification);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
    public void gosemester(View view){
        Intent semester = new Intent(this,SemesterActivity.class);
        startActivity(semester);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}