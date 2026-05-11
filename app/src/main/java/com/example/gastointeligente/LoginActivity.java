package com.example.gastointeligente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText correo, clave;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.etEmail);
        clave = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);
        tvGoToRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Estoy on Start() ", Toast.LENGTH_SHORT).show();
    }

    public void Avanzar(View view) {
        //Usuario: admin y mi clave: jose1234
        String email = correo.getText() != null ? correo.getText().toString() : "";
        String pass = clave.getText() != null ? clave.getText().toString() : "";

        if (email.equals("admin") && pass.equals("1234")) {
            Toast.makeText(this, "Credenciales correctas", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Usuario y/o clave erradas", Toast.LENGTH_LONG).show();
        }
    }
}
