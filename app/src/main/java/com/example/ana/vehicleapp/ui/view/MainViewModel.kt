package com.example.ana.vehicleapp.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ana.vehicleapp.data.model.VehicleWithAutomaker
import com.example.ana.vehicleapp.data.repository.VehicleRepository
import com.example.ana.vehicleapp.util.Result 
import kotlinx.coroutines.launch

class MainViewModel(private val repository: VehicleRepository) : ViewModel() {

    private val _vehicles = MutableLiveData<List<VehicleWithAutomaker>>()
    val vehicles: LiveData<List<VehicleWithAutomaker>> = _vehicles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    init {
        syncData()
    }

    private fun syncData() {
        viewModelScope.launch {
            _isLoading.value = true
            val localData = repository.getLocalVehiclesWithAutomaker()
            if (localData.isEmpty()) {
                when (val result = repository.syncAndGetVehiclesWithAutomaker()) {
                    is Result.Success -> {
                        _vehicles.postValue(result.data)
                        _error.postValue(null)
                    }
                    is Result.Error -> {
                        _error.postValue("Falha ao carregar dados. Verifique sua conexão.")
                    }
                    is Result.Loading -> {

                    }
                }
            } else {
                _vehicles.postValue(localData)
            }
            _isLoading.value = false
        }
    }


    fun refreshLocalList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _vehicles.postValue(repository.getLocalVehiclesWithAutomaker())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteVehicle(vehicleWithAutomaker: VehicleWithAutomaker) {
        viewModelScope.launch {
            repository.deleteVehicle(vehicleWithAutomaker.vehicle.id)
            refreshLocalList()
        }
    }
}