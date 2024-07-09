package com.h4rzel.spacebarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_Cadastro extends AppCompatActivity {

    String[] mensagens = {"Preencha todos os campos!", "Cadastro realizado!"};
    private EditText T02_EdtTx_Nome, T02_EdtTx_RazaoSocial, T02_EdtTx_Telefone, T02_EdtTx_Cnpj,
            T02_EdtTx_Cidade, T02_EdtTx_Endereco, T02_EdtTx_Email, T02_EdtTx_Senha;
    private String nome,razaosocial, cnpj, telefone, cidade, email, endereco, senha;
    private Button T02_AppCmpBtn_Entrar;
    private Switch T02_Swch_ClienteBarbearia;

    private String UserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        IniciarComponentes();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null){
            UserId = currentUser.getUid();
        }

        T02_AppCmpBtn_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = T02_EdtTx_Nome.getText().toString();
                email = T02_EdtTx_Email.getText().toString();
                senha = T02_EdtTx_Senha.getText().toString();
                telefone = T02_EdtTx_Telefone.getText().toString();
                cidade = T02_EdtTx_Cidade.getText().toString();
                razaosocial = T02_EdtTx_RazaoSocial.getText().toString();
                cnpj = T02_EdtTx_Cnpj.getText().toString();
                endereco = T02_EdtTx_Endereco.getText().toString();

                if (T02_Swch_ClienteBarbearia.isChecked()){
                    if (razaosocial.isEmpty() || email.isEmpty() || senha.isEmpty() || cnpj.isEmpty() || endereco.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                           }
                            else {
                                   CadastrarUsuario(v);
                                  }

                } else{
                    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty() || cidade.isEmpty()) {
                        Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                          } else {
                                      CadastrarUsuario(v);
                                  }
                }



            }
        });

        T02_Swch_ClienteBarbearia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModoCadastro();
            }
        });

    }

    private void CadastrarUsuario(View v) {


        String email = T02_EdtTx_Email.getText().toString();
        String senha = T02_EdtTx_Senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    SalvarDadosUsuario();

                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com 6 caracteres!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Conta ja cadastrada!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email invalido!";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar o usuario!";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

    private void IniciarComponentes() {
        T02_EdtTx_Nome = findViewById(R.id.T02_EdtTx_Nome);
        T02_EdtTx_RazaoSocial = findViewById(R.id.T02_EdtTx_RazaoSocial);
        T02_EdtTx_Telefone = findViewById(R.id.T02_EdtTx_Telefone);
        T02_EdtTx_Cnpj = findViewById(R.id.T02_EdtTx_Cnpj);
        T02_EdtTx_Cidade = findViewById(R.id.T02_EdtTx_Cidade);
        T02_EdtTx_Endereco = findViewById(R.id.T02_EdtTx_Endereco);
        T02_EdtTx_Email = findViewById(R.id.T02_EdtTx_Email);
        T02_EdtTx_Senha = findViewById(R.id.T02_EdtTx_Senha);
        T02_AppCmpBtn_Entrar = findViewById(R.id.T02_AppCmpBtn_Entrar);
        T02_Swch_ClienteBarbearia = findViewById(R.id.T02_Swch_ClienteBarbearia);

    }

    private void SalvarDadosUsuario() {
         nome = T02_EdtTx_Nome.getText().toString();
         razaosocial = T02_EdtTx_RazaoSocial.getText().toString();
         telefone = T02_EdtTx_Telefone.getText().toString();
         cnpj = T02_EdtTx_Cnpj.getText().toString();
         cidade = T02_EdtTx_Cidade.getText().toString();
         endereco = T02_EdtTx_Endereco.getText().toString();
         email = T02_EdtTx_Email.getText().toString();

         if (T02_Swch_ClienteBarbearia.isChecked()) {
             CriarCadastroBarbearia();
         } else {
             CriarCadastroCliente();
         }

    }

    private void CriarCadastroCliente(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome",nome);
        cliente.put("telefone",telefone);
        cliente.put("cidade",cidade);
        cliente.put("email",email);
        cliente.put("tipo-cadastro","cliente");
        db.collection("usuarios").document("cliente").collection("clientes").add(cliente).addOnSuccessListener(documentReference -> {
            Log.d("TAG","cliente adicionado com id: " + documentReference.getId());
        })
                .addOnFailureListener(e -> {
                    Log.w("TAG","Erro ao adicionar cliente!",e);
                });
    }

    private void CriarCadastroBarbearia(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> barbearia = new HashMap<>();
        barbearia.put("razaosocial",razaosocial);
        barbearia.put("cnpj",cnpj);
        barbearia.put("endereco",endereco);
        barbearia.put("email",email);
        barbearia.put("tipo-cadastro","barbearia");
        db.collection("usuarios").document("barbearia").collection("barbearias").add(barbearia).addOnSuccessListener(documentReference -> {
                    Log.d("TAG","Barbearia adicionada com id: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG","Erro ao adicionar Barbearia!",e);
                });

    }

    private void ModoCadastro(){
        if (T02_Swch_ClienteBarbearia.isChecked()) {
            T02_EdtTx_RazaoSocial.setVisibility(View.VISIBLE);
            T02_EdtTx_Nome.setVisibility(View.GONE);
            T02_EdtTx_Cidade.setVisibility(View.GONE);
            T02_EdtTx_Telefone.setVisibility(View.GONE);
            T02_EdtTx_Cnpj.setVisibility(View.VISIBLE);
            T02_EdtTx_Endereco.setVisibility(View.VISIBLE);

        } else {
            T02_EdtTx_RazaoSocial.setVisibility(View.GONE);
            T02_EdtTx_Nome.setVisibility(View.VISIBLE);
            T02_EdtTx_Cidade.setVisibility(View.VISIBLE);
            T02_EdtTx_Telefone.setVisibility(View.VISIBLE);
            T02_EdtTx_Cnpj.setVisibility(View.GONE);
            T02_EdtTx_Endereco.setVisibility(View.GONE);
        }
    }

}

