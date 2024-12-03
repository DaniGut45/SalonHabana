package com.example.salonhabana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecapitulacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recapitulacion)

        // Obtener los datos pasados de la actividad anterior
        val nombre = intent.getStringExtra("nombre")
        val telefono = intent.getStringExtra("telefono")
        val fecha = intent.getStringExtra("fecha")
        val tipoEvento = intent.getStringExtra("tipoEvento")
        val tipoCocina = intent.getStringExtra("tipoCocina")
        val numPersonas = intent.getIntExtra("numPersonas", 0)
        val numJornadas = intent.getIntExtra("numJornadas", 0)
        val habitacionesRequeridas = intent.getBooleanExtra("habitacionesRequeridas", false)

        // Construir el texto de recapitulación
        val recapitulacionText = buildString {
            append("Nombre: $nombre\n")
            append("Teléfono: $telefono\n")
            append("Fecha: $fecha\n")
            append("Tipo de Evento: $tipoEvento\n")
            append("Tipo de Cocina: $tipoCocina\n")
            append("Número de Personas: $numPersonas\n")

            // Mostrar las jornadas y habitaciones solo si el tipo de evento es Congreso
            if (tipoEvento == "Congreso") {
                append("Jornadas: $numJornadas\n")
                append("Habitaciones Requeridas: ${if (habitacionesRequeridas) "Sí" else "No"}\n")
            }
        }

        // Establecer el texto en el TextView
        val textViewRecapitulacion = findViewById<TextView>(R.id.textViewRecapitulacion)
        textViewRecapitulacion.text = recapitulacionText

        // Botón para enviar los datos y pasar a la siguiente actividad
        val buttonEnviarDatos: Button = findViewById(R.id.buttonEnviarDatos)
        buttonEnviarDatos.setOnClickListener {
            // Navegar a ActivityFinal sin pasar datos
            val intent = Intent(this, ActivityFinal::class.java)
            startActivity(intent)
        }
    }
}
