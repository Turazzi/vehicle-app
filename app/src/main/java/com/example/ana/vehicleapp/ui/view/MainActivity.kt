package com.example.ana.vehicleapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ana.vehicleapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var vehicleAdapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_vehicles)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        vehicleAdapter = VehicleAdapter(emptyList())
        recyclerView.adapter = vehicleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.vehicles.observe(this) { vehicles ->
            vehicleAdapter.updateVehicles(vehicles)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}