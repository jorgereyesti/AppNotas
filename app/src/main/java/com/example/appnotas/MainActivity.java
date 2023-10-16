package com.example.appnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth ;
    Button login;
    TextView email, password, reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.bt_login);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_pass);
        reg = findViewById(R.id.t_Register);

        email.setText("jorge@gmail.com");
        password.setText("hola123");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                }); //end
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}