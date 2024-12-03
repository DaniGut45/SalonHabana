package com.example.salonhabana

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.EditText
import android.widget.Switch
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DetalleActivity : AppCompatActivity() {

    private lateinit var textViewFecha: TextView
    private lateinit var buttonSelectDate: Button
    private lateinit var spinnerTipoEvento: Spinner
    private lateinit var spinnerTipoCocina: Spinner
    private lateinit var seekBarPersonas: SeekBar
    private lateinit var textViewPersonaCount: TextView
    private lateinit var textViewJornadas: TextView
    private lateinit var editTextJornadas: EditText
    private lateinit var switchHabitaciones: Switch
    private lateinit var buttonContinue: Button

    private var fechaSeleccionada: String? = null
    private var tipoEventoSeleccionado: String? = null
    private var tipoCocinaSeleccionado: String = "No precisa"
    private var numPersonas: Int = 0
    private var numJornadas: Int = 0
    private var habitacionesRequeridas: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Obtener los datos de MainActivity
        val nombre = intent.getStringExtra("nombre")
        val telefono = intent.getStringExtra("telefono")

        textViewFecha = findViewById(R.id.textViewFecha)
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        spinnerTipoEvento = findViewById(R.id.spinnerTipoEvento)
        spinnerTipoCocina = findViewById(R.id.spinnerTipoCocina)
        seekBarPersonas = findViewById(R.id.seekBarPersonas)
        textViewPersonaCount = findViewById(R.id.textViewPersonaCount)
        textViewJornadas = findViewById(R.id.textViewJornadas)
        editTextJornadas = findViewById(R.id.editTextJornadas)
        switchHabitaciones = findViewById(R.id.switchHabitaciones)
        buttonContinue = findViewById(R.id.buttonContinue)

        // Inicialización de Spinner Tipo Evento
        val eventos = arrayOf("Banquete", "Jornada", "Congreso")
        val eventoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventos)
        eventoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoEvento.adapter = eventoAdapter

        // Inicialización de Spinner Tipo Cocina
        val cocinas = arrayOf("Bufé", "Carta", "Pedir cita con el chef", "No precisa")
        val cocinaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cocinas)
        cocinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoCocina.adapter = cocinaAdapter

        // Listener para Spinner Tipo Evento
        spinnerTipoEvento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipoEventoSeleccionado = parentView?.getItemAtPosition(position).toString()
                if (tipoEventoSeleccionado == "Congreso") {
                    textViewJornadas.visibility = View.VISIBLE
                    editTextJornadas.visibility = View.VISIBLE
                    switchHabitaciones.visibility = View.VISIBLE
                } else {
                    textViewJornadas.visibility = View.GONE
                    editTextJornadas.visibility = View.GONE
                    switchHabitaciones.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        // Listener para SeekBar
        seekBarPersonas.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numPersonas = progress
                textViewPersonaCount.text = "$numPersonas personas"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Listener para el botón de seleccionar fecha
        buttonSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    textViewFecha.text = selectedDate
                    fechaSeleccionada = selectedDate
                }, year, month, day)

            datePickerDialog.show()
        }

        // Listener para el botón de continuar
        buttonContinue.setOnClickListener {
            if (fechaSeleccionada == null) {
                Toast.makeText(this, "Por favor, seleccione una fecha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tipoEventoSeleccionado == null) {
                Toast.makeText(this, "Por favor, seleccione el tipo de evento.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Validar el número de jornadas si el tipo de evento es Congreso
            if (tipoEventoSeleccionado == "Congreso") {
                if (editTextJornadas.text.toString().isEmpty()) {
                    Toast.makeText(this, "Por favor, ingrese el número de jornadas.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    // Convertir el valor de editTextJornadas a un número entero
                    numJornadas = editTextJornadas.text.toString().toIntOrNull() ?: 0
                    if (numJornadas <= 0) {
                        Toast.makeText(this, "Por favor, ingrese un número de jornadas válido.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }

            tipoCocinaSeleccionado = spinnerTipoCocina.selectedItem.toString()
            habitacionesRequeridas = switchHabitaciones.isChecked

            // Guardar datos y continuar a la siguiente actividad
            val intent = Intent(this, RecapitulacionActivity::class.java)
            intent.putExtra("nombre", nombre)  // Asegúrate de que nombre no sea null
            intent.putExtra("telefono", telefono)  // Asegúrate de que telefono no sea null
            intent.putExtra("fecha", fechaSeleccionada)
            intent.putExtra("tipoEvento", tipoEventoSeleccionado)
            intent.putExtra("tipoCocina", tipoCocinaSeleccionado)
            intent.putExtra("numPersonas", numPersonas)
            intent.putExtra("numJornadas", numJornadas)
            intent.putExtra("habitacionesRequeridas", habitacionesRequeridas)
            startActivity(intent)
        }
    }
}
