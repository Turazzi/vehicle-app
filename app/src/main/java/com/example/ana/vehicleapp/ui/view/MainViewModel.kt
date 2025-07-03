package com.example.ana.vehicleapp.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ana.vehicleapp.data.model.Vehicle
import com.example.ana.vehicleapp.data.repository.VehicleRepository
import androidx.lifecycle.viewModelScope
import com.example.ana.vehicleapp.data.model.VehicleWithAutomaker
import kotlinx.coroutines.launch


class MainViewModel (private val repository: VehicleRepository) : ViewModel() {

    private val _vehicles = MutableLiveData<List<VehicleWithAutomaker>>()
    val vehicles: LiveData<List<VehicleWithAutomaker>> = _vehicles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        refreshVehicles()
    }

    private fun refreshVehicles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val vehicleList = repository.syncAndGetVehiclesWithAutomaker()
                _vehicles.postValue(vehicleList)
            } catch (e: Exception) {
                _vehicles.postValue(emptyList())
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun deleteVehicle(vehicleWithAutomaker: VehicleWithAutomaker) {
        viewModelScope.launch {
            repository.deleteVehicle(vehicleWithAutomaker.vehicle.id)
            refreshVehicles()
        }
    }


}