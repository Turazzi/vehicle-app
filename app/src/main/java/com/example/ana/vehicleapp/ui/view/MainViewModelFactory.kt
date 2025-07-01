package com.example.ana.vehicleapp.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.ana.vehicleapp.data.local.AppDatabase
import com.example.ana.vehicleapp.data.remote.RetrofitClient
import com.example.ana.vehicleapp.data.repository.VehicleRepository

class MainViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {

            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "vehicle-database"
            ).build()

            val vehicleDao = db.vehicleDao()
            val automakerDao = db.automakerDao()
            val apiService = RetrofitClient.instance

            val repository = VehicleRepository(apiService, vehicleDao, automakerDao)

            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}