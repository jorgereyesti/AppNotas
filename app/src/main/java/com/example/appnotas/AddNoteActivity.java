package com.example.appnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appnotas.model.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    Button add, hour;
    EditText content;
    private DatabaseReference mDataBase;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        add = findViewById(R.id.bt_addNoteFinish);
        hour = findViewById(R.id.bt_add_hour);
        content = findViewById(R.id.et_add_nota);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int chour = cldr.get(Calendar.HOUR_OF_DAY);
                int cminutes = cldr.get(Calendar.MINUTE);
                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog picker = new TimePickerDialog(AddNoteActivity.this,style,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int shour, int sminute) {
                            hour.setText(shour+":"+sminute);
                    }
                }, chour, cminutes, true);
                picker.setTitle("Seleccionar hora:");
                picker.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendContent = content.getText().toString();
                String sendHour = hour.getText().toString();
                SendNote(sendContent, sendHour);
            }
        });
    }

    private void SendNote(String sendContent, String sendHour) {
        String id= mDataBase.push().getKey();
        String uid = auth.getUid();
        Note note = new Note(uid, id, sendHour, sendContent);
        assert uid != null;
        mDataBase.child("Notes").child(id).setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddNoteActivity.this, "Nota agregada.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNoteActivity.this, "Fallo al agregar nota.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}