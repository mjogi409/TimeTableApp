package com.example.googlesignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TimeTableAddFActivity extends AppCompatActivity {
    //data
    private Spinner sp_faculty;
    private Spinner sp_class;
    private Spinner week;
    private Spinner slot;
    private EditText Subject;
    private EditText content;
    private EditText classroom;
    //buttons
    private ImageView timefrom;
    private ImageView timeto;
    private List<String> cl;
    private List<String> cl2;
    //Database
    private FirebaseDatabase fref;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private int hour, minute;
    private TextView timefrom_text;
    private TextView timeto_text;
    private TextView Duration;
    private int hour_time1, minute_time1, hour_time2, minute_time2;
    //button
    private ImageView addtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_add_factivity);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //data
        sp_class = findViewById(R.id.spin_class);
        content = findViewById(R.id.googlelink_edit_text);
        cl = new ArrayList<String>(); // for faculty
        sp_faculty = findViewById(R.id.spin_faculty);
        cl2 = new ArrayList<String>();
        Subject = findViewById(R.id.subject_edit_text);
        week = findViewById(R.id.spin_week);
        slot = findViewById(R.id.spin_slot);
        classroom = findViewById(R.id.classroom_edit_text);
        timefrom = findViewById(R.id.timefrom);
        timeto = findViewById(R.id.timeto);
        timeto_text = findViewById(R.id.timeto_text);
        timefrom_text = findViewById(R.id.timefrom_text);
        fref = FirebaseDatabase.getInstance();
        ref = fref.getReference("Class");
        ref2 = fref.getReference("Faculty");

        Duration = findViewById(R.id.duration);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cl);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cl2);
        sp_faculty.setAdapter(dataAdapter2);
        sp_class.setAdapter(dataAdapter);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    cl.add(snapshot1.getKey());
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl2.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    cl2.add(snapshot1.getKey());
                }
                dataAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        timefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimeTableAddFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        timefrom_text.setText(hourOfDay + ":" + minute);
                    }

                }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        timeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(TimeTableAddFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        timeto_text.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        timeto_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] tokens = timefrom_text.getText().toString().split(":");

                hour_time1 = Integer.parseInt(tokens[0]);
                minute_time1 = Integer.parseInt(tokens[1]);
                String[] tokens2 = timeto_text.getText().toString().split(":");
                hour_time2 = Integer.parseInt(tokens2[0]);
                minute_time2 = Integer.parseInt(tokens2[1]);
                int duration_hour = hour_time2 - hour_time1;
                int duration_minute = minute_time2 - minute_time1;
                Duration.setText(duration_hour + "hr " + duration_minute + "min");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        timefrom_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] tokens = timefrom_text.getText().toString().split(":");

                hour_time1 = Integer.parseInt(tokens[0]);
                minute_time1 = Integer.parseInt(tokens[1]);
                String[] tokens2 = timeto_text.getText().toString().split(":");
                hour_time2 = Integer.parseInt(tokens2[0]);
                minute_time2 = Integer.parseInt(tokens2[1]);
                int duration_hour = hour_time2 - hour_time1;
                int duration_minute = minute_time2 - minute_time1;
                Duration.setText(duration_hour + "hr " + duration_minute + "min");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addtt = findViewById(R.id.addtimetable_btn);
        addtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap tt = new HashMap();
                tt.put("Subject",Subject.getText().toString());
                tt.put("timefrom",timefrom_text.getText().toString());
                tt.put("timeto",timeto_text.getText().toString());
                tt.put("duration",Duration.getText().toString());
                tt.put("Faculty",sp_faculty.getSelectedItem().toString());
                tt.put("Content",content.getText().toString());
                tt.put("classroom",classroom.getText().toString());

                ref2.child(sp_faculty.getSelectedItem().toString()).child("TimeTable").child(week.getSelectedItem().toString()).child(slot.getSelectedItem().toString()).setValue(tt);
            }
        });





    }
}