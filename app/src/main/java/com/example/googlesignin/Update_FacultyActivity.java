package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Update_FacultyActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;

    //data
    private EditText Erp;
    private EditText Email;
    private EditText Sittings;
    //buttons
    private ImageView updater_f;
    private ImageView fetcher;
    private EditText fullname;

    List<Faculty> facultyList;

    //Database
    DatabaseReference ref;
    FirebaseDatabase fref;
    DatabaseReference ref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
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

        fullname = findViewById(R.id.fullname_edit_text);
        //data
        Erp = findViewById(R.id.erp_edit_text);
        Email = findViewById(R.id.email_edit_text);
        Sittings = findViewById(R.id.sittings_edit_text);
        //buttons
        updater_f = findViewById(R.id.updater_f);
        fetcher = findViewById(R.id.fetcher);

        //database
        fref = FirebaseDatabase.getInstance();
        ref = fref.getReference();
        ref2 = fref.getReference().child("Faculty");


        //onclick
        fetcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify(Erp.getText().toString())) {
                    Query query = ref2.child(Erp.getText().toString()).child("email");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Email.setText(snapshot.getValue(String.class));
                            //Sittings.setText(snapper.child("sittings").getValue(String.class));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Update_FacultyActivity.this, "Sorry can't fetch the data", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Query query2 = ref2.child(Erp.getText().toString()).child("office");
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                                //Email.setText(snapper.getValue(String.class));
                                Sittings.setText(snapshot.getValue(String.class));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Update_FacultyActivity.this, "Sorry can't fetch the data", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Query query3 = ref2.child(Erp.getText().toString()).child("Name");
                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            //Email.setText(snapper.getValue(String.class));
                            fullname.setText(snapshot.getValue(String.class));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Update_FacultyActivity.this, "Sorry can't fetch the data", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Update_FacultyActivity.this, "Enter Erp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updater_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify(Erp.getText().toString())) {

                    HashMap faculty = new HashMap();
                    faculty.put("Name",fullname.getText().toString());
                    faculty.put("email", Email.getText().toString());
                    faculty.put("office", Sittings.getText().toString());

                    ref2.child(Erp.getText().toString()).updateChildren(faculty);

                } else {
                    Toast.makeText(Update_FacultyActivity.this, "Enter Erp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean verify(String erp) {
        if (!erp.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}