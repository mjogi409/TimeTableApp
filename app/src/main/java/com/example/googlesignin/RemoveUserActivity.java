package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class RemoveUserActivity extends AppCompatActivity {
    private TextView email;
    private TextView name;
    private TextView id;
    private ImageView pfp;
    private TextView tv1;
    private String date;
    private SimpleDateFormat formatter;
    private Date today;
    //code
    private EditText erp;
    private Spinner sp;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
//button remove
    private ImageView removeuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //firebase instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //firebasedatabase Instance



        //profile
        name = findViewById(R.id.user_name);
        pfp = findViewById(R.id.profile_photo);
        name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(pfp);
        today = Calendar.getInstance().getTime();//getting date
        formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        date = formatter.format(today);
        tv1 = findViewById(R.id.date);
        tv1.setText(date);

        erp = findViewById(R.id.erp_edit_text);
        sp = findViewById(R.id.choose);

        removeuser = findViewById(R.id.remove_user_btn);
        //String erptext = erp.getText();
        removeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(erp.getText().toString().isEmpty() == false) {
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference(sp.getSelectedItem().toString()).child(erp.getText().toString());
                    //Query query = databaseReference.child(sp.getSelectedItem().toString()).orderByChild("erp").equalTo(erp.getText().toString());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot snapper : snapshot.getChildren()) {
                                snapper.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(RemoveUserActivity.this, "Please enter erp", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}