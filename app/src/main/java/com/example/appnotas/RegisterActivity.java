package com.example.appnotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnotas.Adapter.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText NombreET, CorreoET, ContraseñaET,ConfirmarcontraseñaET;
    Button RegistrarUsuario;
    TextView TengounacuentaTXT;
    FirebaseAuth firebaseAuth;
    String nombre =" ", correo = " ", password = " ", confirmarpassword = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        NombreET = findViewById(R.id.NombreET);
        CorreoET = findViewById(R.id.CorreoET);
        ContraseñaET = findViewById(R.id.ContraseñaET);
        ConfirmarcontraseñaET = findViewById(R.id.ConfirmarcontraseñaET);
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario);
        TengounacuentaTXT = findViewById(R.id.TengounacuentaTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        nombre = NombreET.getText().toString();
        correo = CorreoET.getText().toString();
        password = ContraseñaET.getText().toString();
        confirmarpassword = ConfirmarcontraseñaET.getText().toString();
        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                ValidarDatos();
            }
        });

    }
    private void ValidarDatos() {//Crear usuario firebase
        nombre = NombreET.getText().toString();
        correo = CorreoET.getText().toString();
        password = ContraseñaET.getText().toString();
        confirmarpassword = ConfirmarcontraseñaET.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //
                        GuardarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(RegisterActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void GuardarInformacion() {

        //Obtener la identificacion de usuario actual
        String uid = firebaseAuth.getUid();
        Log.d("RegisterActivity", "UID del usuario: " + uid);
        User.getInstance().setUserUid(uid);
        //Configurar datos para agregar en la base de datos
        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("correo", correo);
        Datos.put("nombres", nombre);
        Datos.put("password", password);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Cuenta Creada con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}