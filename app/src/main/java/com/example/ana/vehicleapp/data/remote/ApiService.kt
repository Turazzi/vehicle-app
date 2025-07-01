package com.example.ana.vehicleapp.data.remote

import com.example.ana.vehicleapp.data.model.Automaker
import com.example.ana.vehicleapp.data.model.Vehicle
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("manufactures")
    suspend fun getAutomakers(): Response<List<Automaker>>

    @GET("vehicles")
    suspend fun getVehicles(): Response<List<Vehicle>>

}