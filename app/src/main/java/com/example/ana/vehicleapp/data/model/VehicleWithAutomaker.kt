package com.example.ana.vehicleapp.data.model

import androidx.room.Embedded

data class VehicleWithAutomaker (

    @Embedded
    val vehicle: Vehicle,
    val automakerName: String

)