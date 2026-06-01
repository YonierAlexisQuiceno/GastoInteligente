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
        String email = correo.getText() != null ? correo.getText().toString().trim() : "";
        String pass = clave.getText() != null ? clave.getText().toString().trim() : "";

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el correo y la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals("admin") && pass.equals("admin")) {
            Toast.makeText(this, "Bienvenido Administrador", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("ROL", "ADMIN");
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Bienvenido Usuario", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("ROL", "USER");
            startActivity(intent);
            finish();
        }
    }
}
