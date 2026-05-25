package com.example.gastointeligente;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView lvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lvUsuarios = findViewById(R.id.lvUsuarios);

        // Consultar todos los registros con Sugar ORM
        List<Usuario> listaUsuarios = Usuario.listAll(Usuario.class);

        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
            // Transformar la lista de objetos a lista de Strings para el adaptador básico
            List<String> datosFormateados = new ArrayList<>();
            for (Usuario u : listaUsuarios) {
                String texto = "C.C: " + u.getCedula() + " - " + u.getNombre() + " (" + u.getTelefono() + ")";
                datosFormateados.add(texto);
            }

            // Usar ArrayAdapter como el profesor mencionó
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosFormateados);
            lvUsuarios.setAdapter(adaptador);

            // Detectar clicks en los elementos de la lista (opcional, como el docente lo sugirió)
            lvUsuarios.setOnItemClickListener((parent, view, position, id) -> {
                String seleccionado = datosFormateados.get(position);
                Toast.makeText(this, "Seleccionaste: " + seleccionado, Toast.LENGTH_SHORT).show();
            });

        } else {
            Toast.makeText(this, "No hay usuarios registrados aún.", Toast.LENGTH_SHORT).show();
        }
    }
}
