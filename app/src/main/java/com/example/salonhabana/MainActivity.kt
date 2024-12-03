package com.example.salonhabana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener las referencias de los EditText y el botón
        val nombreEditText: EditText = findViewById(R.id.editTextText)
        val telefonoEditText: EditText = findViewById(R.id.editTextPhone)
        val boton: Button = findViewById(R.id.button)

        // Configurar el clic del botón
        boton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val telefono = telefonoEditText.text.toString().trim()

            // Validaciones
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (telefono.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su número de teléfono", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!telefono.matches(Regex("^[0-9]+$"))) {
                Toast.makeText(this, "El número de teléfono debe ser solo números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Al pasar los datos a DetalleActivity, incluimos el nombre y teléfono
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("telefono", telefono)
            startActivity(intent)
        }
    }
}

