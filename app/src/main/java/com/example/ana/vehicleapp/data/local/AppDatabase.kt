package com.example.ana.vehicleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ana.vehicleapp.data.model.Automaker
import com.example.ana.vehicleapp.data.model.Vehicle

@Database(entities = [Automaker::class, Vehicle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun automakerDao(): AutomakerDAO
    abstract fun vehicleDao(): VehicleDAO

}