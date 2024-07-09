package com.h4rzel.spacebarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity {

    private TextView T01_TxtVw_CliqueAquiNaoTemConta,T01_TxtVw_clickAquiNaoTemSenha;
    private EditText T01_EdtTx_Email, T01_EdtTx_Senha;
    private AppCompatButton T01_AppCmpBtn_Entrar;

    private String Email,Senha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        T01_TxtVw_CliqueAquiNaoTemConta = findViewById(R.id.T01_TxtVw_CliqueAquiNaoTemConta);
        T01_TxtVw_clickAquiNaoTemSenha = findViewById(R.id.T01_TxtVw_clickAquiNaoTemSenha);
        T01_EdtTx_Email = findViewById(R.id.T01_EdtTx_Email);
        T01_EdtTx_Senha = findViewById(R.id.T01_EdtTx_Senha);
        T01_AppCmpBtn_Entrar = findViewById(R.id.T01_AppCmpBtn_Entrar);

        T01_AppCmpBtn_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = T01_EdtTx_Email.getText().toString();
                Senha = T01_EdtTx_Senha.getText().toString();
                if (Email.isEmpty() || Senha.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "Preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                } else {
                    AutenticarUsuario();
                }
            }
        });
        T01_TxtVw_clickAquiNaoTemSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_RecuperarSenha.class);
                startActivity(intent);
            }
        });
        T01_TxtVw_CliqueAquiNaoTemConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_Cadastro.class);
                startActivity(intent);

            }
        });


    }
        private void AutenticarUsuario(){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(Email,Senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful() ){
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           Intent intent = new Intent(Activity_Login.this, Activity_MenuPrincipal.class);
                           startActivity(intent);
                       }
                   },1000);

               } else {
                   String error;
                   try {
                       throw task.getException();
                   }
                   catch (Exception e){
                       error = "Erro ao logar usuário! ";
                   }
               }

                }
            });
        }

    }
