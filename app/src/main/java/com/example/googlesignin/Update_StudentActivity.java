package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Update_StudentActivity extends AppCompatActivity {
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
    private Spinner sp;
    private List<String> cl;
    private EditText fullname;
    //buttons
    private ImageView updater_s;
    private ImageView fetcher;


    //Database
    DatabaseReference ref;
    FirebaseDatabase fref;
    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
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
        Erp = findViewById(R.id.erp_edit_text);
        Email = findViewById(R.id.email_edit_text);
        sp = findViewById(R.id.spinner);
        cl = new ArrayList<String>();
fullname = findViewById(R.id.fullname_edit_text);
        //database
        fref = FirebaseDatabase.getInstance();
        ref = fref.getReference("Class");
        ref2 = fref.getReference().child("Students");

        //buttons
        fetcher = findViewById(R.id.fetcher);
        updater_s = findViewById(R.id.updatestudent_btn);

        //for spinner (class selection)

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cl);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cl.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cl.add(snapshot.getKey());
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_StudentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        fetcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify(Erp.getText().toString())) {
                    Query query = ref2.child(Erp.getText().toString()).child("email");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Email.setText(snapshot.getValue(String.class));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Update_StudentActivity.this, "Sorry can't fetch the data", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Query query2 = ref2.child(Erp.getText().toString()).child("class");
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                sp.setSelection(cl.indexOf(snapshot.getValue().toString()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Query query3 = ref2.child(Erp.getText().toString()).child("Name");
                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            fullname.setText(snapshot.getValue(String.class));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Update_StudentActivity.this, "Sorry can't fetch the data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Update_StudentActivity.this, "Enter Erp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updater_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify(Erp.getText().toString())){
                    HashMap student = new HashMap();
                    student.put("Name",fullname.getText().toString());
                    student.put("email", Email.getText().toString());
                    student.put("class", sp.getSelectedItem().toString());
                    ref2.child(Erp.getText().toString()).updateChildren(student);
                }
                else
                {
                    Toast.makeText(Update_StudentActivity.this, "Enter Erp", Toast.LENGTH_SHORT).show();
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