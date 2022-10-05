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

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private ImageView pfp;
    private FirebaseAuth mAuth;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        name = findViewById(R.id.user_name);
        name.setText(currentUser.getDisplayName());
        pfp = findViewById(R.id.profile_photo);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pfp);
    }
    public void goaboutsus(View view)
    {
        Intent i = new Intent(this,AboutusActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void signout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}