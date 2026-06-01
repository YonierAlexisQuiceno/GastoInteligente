package com.example.gastointeligente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nombre, cedula, telefono;
    Button btnRegistrar, btnConsultar, btnActualizar, btnEliminar, btnVerAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "onCreate() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onCreate() invocado");

        nombre = findViewById(R.id.etNombre);
        cedula = findViewById(R.id.etCedula);
        telefono = findViewById(R.id.etTelefono);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnVerAdmin = findViewById(R.id.btnVerAdmin);

        // Operación CREATE (Registrar)
        btnRegistrar.setOnClickListener(v -> {
            String strNombre = nombre.getText().toString().trim();
            String strCedula = cedula.getText().toString().trim();
            String strTelefono = telefono.getText().toString().trim();

            if (strNombre.isEmpty() || strCedula.isEmpty() || strTelefono.isEmpty()) {
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int intCedula = Integer.parseInt(strCedula);
                int intTelefono = Integer.parseInt(strTelefono);

                Usuario usuario = new Usuario(intCedula, strNombre, intTelefono);
                usuario.save(); // Sugar ORM Save

                Toast.makeText(this, "Usuario guardado con éxito", Toast.LENGTH_LONG).show();
                limpiarCampos();

            } catch (NumberFormatException e) {
                // Prevención del crash reportado por el usuario
                Toast.makeText(this, "Error: Cédula o Teléfono son demasiado largos para un número", Toast.LENGTH_LONG).show();
            }
        });

        // Operación READ (Consultar)
        btnConsultar.setOnClickListener(v -> {
            String strCedula = cedula.getText().toString().trim();
            if (strCedula.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa la cédula a buscar", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int intCedula = Integer.parseInt(strCedula);
                List<Usuario> lista = Usuario.find(Usuario.class, "cedula = ?", String.valueOf(intCedula));

                if (lista != null && !lista.isEmpty()) {
                    Usuario user = lista.get(0);
                    nombre.setText(user.getNombre());
                    telefono.setText(String.valueOf(user.getTelefono()));
                    Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Cédula inválida", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración por Rol
        String rol = getIntent().getStringExtra("ROL");
        if (!"ADMIN".equals(rol)) {
            btnActualizar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            btnVerAdmin.setVisibility(View.GONE);
        }

        // Ir al panel de administrador
        btnVerAdmin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdminActivity.class));
        });
    }

    // Operación UPDATE (Actualizar) usando onClick en XML
    public void actualizar(View view) {
        String strCedula = cedula.getText().toString().trim();
        String strNombre = nombre.getText().toString().trim();
        String strTelefono = telefono.getText().toString().trim();

        if (strCedula.isEmpty() || strNombre.isEmpty() || strTelefono.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int intCedula = Integer.parseInt(strCedula);
            List<Usuario> lista = Usuario.find(Usuario.class, "cedula = ?", String.valueOf(intCedula));

            if (lista != null && !lista.isEmpty()) {
                Usuario user = lista.get(0);
                user.setNombre(strNombre);
                user.setTelefono(Integer.parseInt(strTelefono));
                user.save(); // Sugar ORM Save
                Toast.makeText(this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se encontró el usuario para actualizar", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Datos numéricos inválidos o muy largos", Toast.LENGTH_SHORT).show();
        }
    }

    // Operación DELETE (Eliminar) usando onClick en XML
    public void eliminar(View view) {
        String strCedula = cedula.getText().toString().trim();
        if (strCedula.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa la cédula a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int intCedula = Integer.parseInt(strCedula);
            List<Usuario> lista = Usuario.find(Usuario.class, "cedula = ?", String.valueOf(intCedula));

            if (lista != null && !lista.isEmpty()) {
                Usuario user = lista.get(0);
                user.delete(); // Sugar ORM Delete
                Toast.makeText(this, "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se encontró el usuario a eliminar", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cédula inválida", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        nombre.setText("");
        cedula.setText("");
        telefono.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onStart() invocado");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onResume() invocado");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onPause() invocado");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onStop() invocado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy() invocado", Toast.LENGTH_SHORT).show();
        Log.d("CICLO_VIDA", "onDestroy() invocado");
    }
}