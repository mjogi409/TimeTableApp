package com.example.googlesignin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SEEActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_seeactivity);
        Objects.requireNonNull(getSupportActionBar()).hide();
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl("https://canvas.rku.ac.in/login/canvas");
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
}