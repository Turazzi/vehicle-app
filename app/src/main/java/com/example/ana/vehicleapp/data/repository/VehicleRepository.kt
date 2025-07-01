package com.example.ana.vehicleapp.data.repository

import android.util.Log
import com.example.ana.vehicleapp.data.local.AutomakerDAO
import com.example.ana.vehicleapp.data.local.VehicleDAO
import com.example.ana.vehicleapp.data.model.Vehicle
import com.example.ana.vehicleapp.data.remote.ApiService

class VehicleRepository (
    private val apiService: ApiService,
    private val vehicleDao: VehicleDAO,
    private val automakerDao: AutomakerDAO
) {

    suspend fun syncAndGetVehicles(): List<Vehicle> {

        try {
            val vehicleResponse = apiService.getVehicles()
            val automakerRespose = apiService.getAutomakers()

            if (vehicleResponse.isSuccessful && automakerRespose.isSuccessful) {
                val vehicles = vehicleResponse.body()
                val automakers = automakerRespose.body()

                if (vehicles != null && automakers != null) {
                    automakerDao.insertAll(automakers)
                    vehicleDao.insertAll(vehicles)
                    Log.d("VehicleRepository", "Data synced from API sucessfully")
                }

            }
        } catch (e: Exception) {
            Log.e("VehicleRepository", "Failed to sync data from API", e)
        }

        return vehicleDao.getAll()
    }

    suspend fun insertVehicle(vehicle: Vehicle) {
        vehicleDao.insert(vehicle)
    }

    suspend fun deleteVehicle(vehicleId: Int) {
        vehicleDao.deleteById(vehicleId)
    }

}