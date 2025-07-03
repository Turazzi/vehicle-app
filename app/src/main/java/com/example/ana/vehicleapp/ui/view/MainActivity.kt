package com.example.ana.vehicleapp.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ana.vehicleapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var vehicleAdapter: VehicleAdapter

    private val detailActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.refreshLocalList()
            Toast.makeText(this, "Lista atualizada!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val factory = MainViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_vehicles)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        vehicleAdapter = VehicleAdapter(emptyList())
        recyclerView.adapter = vehicleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        registerForContextMenu(recyclerView)

        viewModel.vehicles.observe(this) { vehicles ->
            vehicleAdapter.updateVehicles(vehicles)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_vehicle -> {
                val intent = Intent(this, VehicleDetailActivity::class.java)
                intent.putExtra("MODE", "ADD")
                detailActivityLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = vehicleAdapter.longClickedPosition
        if (position == -1) return super.onContextItemSelected(item)
        val selectedVehicle = viewModel.vehicles.value?.get(position) ?: return super.onContextItemSelected(item)
        val intent = Intent(this, VehicleDetailActivity::class.java)

        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteVehicle(selectedVehicle)
                true
            }
            R.id.action_edit -> {
                intent.putExtra("MODE", "EDIT")
                intent.putExtra("VEHICLE", selectedVehicle.vehicle)
                detailActivityLauncher.launch(intent)
                true
            }
            R.id.action_details -> {
                intent.putExtra("MODE", "VIEW")
                intent.putExtra("VEHICLE", selectedVehicle.vehicle)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.refreshLocalList()
    }
}