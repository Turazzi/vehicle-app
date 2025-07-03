package com.example.ana.vehicleapp.ui.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.ana.vehicleapp.R
import com.example.ana.vehicleapp.data.model.Automaker
import com.example.ana.vehicleapp.data.model.Vehicle
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: VehicleDetailViewModel
    private var currentVehicle: Vehicle? = null
    private var viewMode: String = "ADD"

    private lateinit var modelEditText: TextInputEditText
    private lateinit var yearEditText: TextInputEditText
    private lateinit var motorizationEditText: TextInputEditText
    private lateinit var automakerSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = VehicleDetailViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[VehicleDetailViewModel::class.java]

        bindViews()
        handleIntent()
        setupUI()
        setupObservers()
        setupClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun bindViews() {
        modelEditText = findViewById(R.id.edit_text_model)
        yearEditText = findViewById(R.id.edit_text_year)
        motorizationEditText = findViewById(R.id.edit_text_motorization)
        automakerSpinner = findViewById(R.id.spinner_automaker)
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)
    }

    private fun handleIntent() {
        viewMode = intent.getStringExtra("MODE") ?: "ADD"

        if (viewMode == "EDIT" || viewMode == "VIEW") {
            currentVehicle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("VEHICLE", Vehicle::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("VEHICLE")
            }
        }
    }

    private fun setupUI() {
        currentVehicle?.let {
            modelEditText.setText(it.model)
            yearEditText.setText(it.year.toString())
            motorizationEditText.setText(it.motorization)
        }

        if (viewMode == "VIEW") {
            title = "Detalhes do Veículo"
            modelEditText.isEnabled = false
            yearEditText.isEnabled = false
            motorizationEditText.isEnabled = false
            automakerSpinner.isEnabled = false
            saveButton.visibility = View.GONE
            cancelButton.text = "Voltar"
        } else {
            title = if (viewMode == "EDIT") "Editar Veículo" else "Adicionar Veículo"
        }
    }

    private fun setupObservers() {
        viewModel.automakers.observe(this) { automakers ->
            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_item_dark,
                automakers.map { it.name }
            )
            adapter.setDropDownViewResource(R.layout.spinner_item_dark)
            automakerSpinner.adapter = adapter

            currentVehicle?.let { vehicle ->
                val automakerPosition = automakers.indexOfFirst { it.id == vehicle.automakerId }
                if (automakerPosition != -1) {
                    automakerSpinner.setSelection(automakerPosition)
                }
            }

            viewModel.finishOperation.observe(this) { finished ->
                if (finished) {
                    Toast.makeText(this, "Operação concluída com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        viewModel.finishOperation.observe(this) { finished ->
            if (finished) {
                Toast.makeText(this, "Operação concluída com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupClickListeners() {
        cancelButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            saveVehicleData()
        }

    }


    private fun saveVehicleData() {
        val model = modelEditText.text.toString().trim()
        val yearStr = yearEditText.text.toString().trim()
        val motorization = motorizationEditText.text.toString().trim()
        val selectedAutomaker = viewModel.automakers.value?.getOrNull(automakerSpinner.selectedItemPosition)

        if (model.isEmpty() || yearStr.isEmpty() || motorization.isEmpty() || selectedAutomaker == null) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val year = yearStr.toIntOrNull()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if (year == null || year > currentYear) {
            Toast.makeText(this, "Ano inválido. Por favor, insira um ano válido que não seja no futuro.", Toast.LENGTH_SHORT).show()
            return
        }

        val specialCharRegex = "^[a-zA-Z0-9 .\\-]*$".toRegex()
        if (!model.matches(specialCharRegex) || !motorization.matches(specialCharRegex)) {
            Toast.makeText(this, "Os campos Modelo e Motorização não podem conter caracteres especiais.", Toast.LENGTH_SHORT).show()
            return
        }

        if (viewMode == "EDIT") {
            val updatedVehicle = Vehicle(
                id = currentVehicle!!.id,
                model = model,
                automakerId = selectedAutomaker.id,
                motorization = motorization,
                year = year
            )
            viewModel.updateVehicle(updatedVehicle)
        } else {
            val newVehicle = Vehicle(
                id = 0,
                model = model,
                automakerId = selectedAutomaker.id,
                motorization = motorization,
                year = year
            )
            viewModel.insertVehicle(newVehicle)
        }
    }
}