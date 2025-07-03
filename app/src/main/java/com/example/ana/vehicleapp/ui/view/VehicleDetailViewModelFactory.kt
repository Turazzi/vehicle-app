package com.example.ana.vehicleapp.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.ana.vehicleapp.data.local.AppDatabase
import com.example.ana.vehicleapp.data.remote.RetrofitClient
import com.example.ana.vehicleapp.data.repository.VehicleRepository

class VehicleDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleDetailViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "vehicle-database"
            ).build()

            val repository = VehicleRepository(
                RetrofitClient.instance,
                db.vehicleDao(),
                db.automakerDao()
            )

            @Suppress("UNCHECKED_CAST")
            return VehicleDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}