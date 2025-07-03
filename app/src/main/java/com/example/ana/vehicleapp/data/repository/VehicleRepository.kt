package com.example.ana.vehicleapp.data.repository

import android.util.Log
import com.example.ana.vehicleapp.data.local.AutomakerDAO
import com.example.ana.vehicleapp.data.local.VehicleDAO
import com.example.ana.vehicleapp.data.model.Automaker
import com.example.ana.vehicleapp.data.model.Vehicle
import com.example.ana.vehicleapp.data.model.VehicleWithAutomaker
import com.example.ana.vehicleapp.data.remote.ApiService
import com.example.ana.vehicleapp.util.Result

class VehicleRepository(
    private val apiService: ApiService,
    private val vehicleDao: VehicleDAO,
    private val automakerDao: AutomakerDAO
) {

    suspend fun syncAndGetVehiclesWithAutomaker(): Result<List<VehicleWithAutomaker>> {
        return try {
            val vehicleResponse = apiService.getVehicles()
            val automakerResponse = apiService.getAutomakers()

            if (vehicleResponse.isSuccessful && automakerResponse.isSuccessful) {
                val vehicles = vehicleResponse.body()
                val automakers = automakerResponse.body()

                if (vehicles != null && automakers != null) {
                    automakerDao.insertAll(automakers)
                    vehicleDao.insertAll(vehicles)
                    Log.d("VehicleRepository", "Data synced from API successfully.")
                }
            }
            Result.Success(vehicleDao.getVehiclesWithAutomaker())
        } catch (e: Exception) {
            Log.e("VehicleRepository", "Failed to sync data from API", e)
            Result.Error(e)
        }
    }

    suspend fun getAllAutomakers(): List<Automaker> {
        return automakerDao.getAll()
    }

    suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.update(vehicle)
    }

    suspend fun insertVehicle(vehicle: Vehicle) {
        vehicleDao.insert(vehicle)
    }

    suspend fun deleteVehicle(vehicleId: Int) {
        vehicleDao.deleteById(vehicleId)
    }

    suspend fun getLocalVehiclesWithAutomaker(): List<VehicleWithAutomaker> {
        return vehicleDao.getVehiclesWithAutomaker()
    }
}