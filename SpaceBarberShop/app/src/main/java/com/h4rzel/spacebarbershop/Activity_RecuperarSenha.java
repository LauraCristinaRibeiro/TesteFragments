package com.h4rzel.spacebarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_RecuperarSenha extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText T03_EdtTxt_Email;
    private Button T03_Btn_Enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        mAuth = FirebaseAuth.getInstance();
        T03_EdtTxt_Email = findViewById(R.id.T03_EdtTxt_Email);
        T03_Btn_Enviar = findViewById(R.id.T03_Btn_Enviar);
        T03_Btn_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = T03_EdtTxt_Email.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(Activity_RecuperarSenha.this,"Entre com seu e-mail",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Activity_RecuperarSenha.this,"E-mail de recuperação de senha enviado!",Toast.LENGTH_SHORT).show();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Activity_RecuperarSenha.this,"Error: " + error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
}