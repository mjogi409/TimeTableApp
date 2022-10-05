package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {
    private EditText sittings;
    private Spinner classes;
    private RadioButton faculty;
    private RadioButton student;
    private EditText erp;
    private EditText emaile;


    //profile
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    private ImageView adduserbtn;
    private EditText fullname;
//firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //firebase instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //firebasedatabase Instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Class");
        //basic info of add user
        erp = findViewById(R.id.erp_edit_text);
        emaile = findViewById(R.id.email_edit_text);
        sittings = findViewById(R.id.sittings_edit_text);
        classes = findViewById(R.id.spinner);
        faculty = findViewById(R.id.faculty);
        student= findViewById(R.id.student);
        fullname = findViewById(R.id.fullname_edit_text);
        List<String> cl = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cl);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(dataAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cl.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    cl.add(snapshot.getKey());
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddUserActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });



        //topbar setup
        name = findViewById(R.id.user_name);
        pfp = findViewById(R.id.profile_photo);
        name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pfp);
        today = Calendar.getInstance().getTime();//getting date
        formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        date = formatter.format(today);
        tv1 = findViewById(R.id.date);
        tv1.setText(date);


        adduserbtn = findViewById(R.id.adduserbtn);
        adduserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyandsend();
            }
        });
    }

    private void verifyandsend() {
        if(faculty.isChecked())
        {
            if(erp.getText().toString().isEmpty() || emaile.getText().toString().isEmpty() || sittings.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Missing Information", Toast.LENGTH_SHORT).show();
            }
            else
            {
                databaseReference = firebaseDatabase.getReference("Faculty").child(erp.getText().toString());
                HashMap faculty = new HashMap();
                faculty.put("Name",fullname.getText().toString());

                faculty.put("email",emaile.getText().toString());
                faculty.put("office",sittings.getText().toString());
                //Faculty faculty = new Faculty(erp.getText().toString(),emaile.getText().toString(),sittings.getText().toString());
                databaseReference.setValue(faculty);
                Toast.makeText(this, "Data has been inserted for faculty "+erp.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
        else if(student.isChecked())
        {
            if(erp.getText().toString().isEmpty() || emaile.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Missing Information", Toast.LENGTH_SHORT).show();
            }
            else
            {
                databaseReference = firebaseDatabase.getReference("Students").child(erp.getText().toString());
                HashMap student = new HashMap();
                student.put("Name",fullname.getText().toString());
                student.put("email",emaile.getText().toString());
                student.put("class",classes.getSelectedItem().toString());
                //Students students = new Students(erp.getText().toString(),emaile.getText().toString(),classes.getSelectedItem().toString());
                databaseReference.setValue(student);
                Toast.makeText(this, "Data has been inserted for student "+erp.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}