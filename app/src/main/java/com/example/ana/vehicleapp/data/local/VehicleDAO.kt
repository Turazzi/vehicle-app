package com.example.ana.vehicleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ana.vehicleapp.data.model.Vehicle


@Dao
interface VehicleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<Vehicle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle)

    @Query("SELECT * FROM vehicles")
    suspend fun getAll(): List<Vehicle>

    @Query("DELETE FROM vehicles WHERE id = :vehicleId")
    suspend fun delete(vehicleId: Int)

}