package com.h4rzel.spacebarbershop;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;
    private TextView textViewName, textViewEmail;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        RecuperarUsuario();

        // Configurar Toolbar personalizada
        Toolbar toolbar = findViewById(R.id.T04_Toolbar_MenuPrincipal);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Menu Principal");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.T04_NavVw_MenuLateral);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Carregar o fragmento inicial
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.T04_FrmLyt_Telas, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.MMN00_Itm_Agendamentos);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void RecuperarUsuario(){

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        navigationView = findViewById(R.id.T04_NavVw_MenuLateral);

        View headerView = navigationView.getHeaderView(0);

        textViewName = headerView.findViewById(R.id.MMN00_TxtVw_NomeUsuario);
        textViewEmail = headerView.findViewById(R.id.MMN00_TxtVw_EmailUsuario);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obter o usuário atualmente autenticado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userEmail = currentUser.getEmail();
        textViewEmail.setText(userEmail);

        if (currentUser != null) {
            db.collection("usuarios")
                    .document("cliente")
                    .collection("clientes")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                textViewName.setText(document.getString("nome"));
                            } else {
                                // Nenhum documento encontrado com o email especificado
                            }
                        } else {
                            // Falha na busca dos documentos
                        }
                    });
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selecionaFragmento = null;
        switch (item.getItemId()) {
            case R.id.MMN00_Itm_Perfil:
                // handle click
                selecionaFragmento = new Fragment_Perfil();
                Toast.makeText(Activity_MenuPrincipal.this, "Tela Perfil", Toast.LENGTH_LONG);
                break;
            case R.id.MMN00_Itm_Barbearia:
                // handle click

                break;
            case R.id.MMN00_Itm_Agendamentos:
                // handle click
                selecionaFragmento = new Fragment_Home();
                break;
            case R.id.MMN00_Itm_Sobre:
                // handle click
                break;
            case R.id.MMN00_Itm_Logout:
                // handle click
                break;
        }

        if (selecionaFragmento != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.T04_FrmLyt_Telas, selecionaFragmento).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
