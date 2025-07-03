package com.example.ana.vehicleapp.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ana.vehicleapp.data.model.Automaker
import com.example.ana.vehicleapp.data.model.Vehicle
import com.example.ana.vehicleapp.data.repository.VehicleRepository
import kotlinx.coroutines.launch

class VehicleDetailViewModel(private val repository: VehicleRepository) : ViewModel() {

    private val _automakers = MutableLiveData<List<Automaker>>()
    val automakers: LiveData<List<Automaker>> = _automakers

    private val _finishOperation = MutableLiveData<Boolean>()
    val finishOperation: LiveData<Boolean> = _finishOperation

    init {
        loadAutomakers()
    }

    private fun loadAutomakers() {
        viewModelScope.launch {
            _automakers.value = repository.getAllAutomakers()
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.updateVehicle(vehicle)
            _finishOperation.value = true
        }
    }

    fun insertVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.insertVehicle(vehicle)
            _finishOperation.value = true
        }
    }
}