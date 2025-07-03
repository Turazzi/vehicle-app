package com.example.ana.vehicleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ana.vehicleapp.data.model.Vehicle
import com.example.ana.vehicleapp.data.model.VehicleWithAutomaker

@Dao
interface VehicleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<Vehicle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle)

    @Query("SELECT * FROM vehicles")
    suspend fun getAll(): List<Vehicle>

    @Query("DELETE FROM vehicles WHERE id = :vehicleId")
    suspend fun deleteById(vehicleId: Int)

    @Query("""
    SELECT v.*, a.name as automakerName
    FROM vehicles as v
    INNER JOIN automakers as a ON v.automakerId = a.id
    """)
    suspend fun getVehiclesWithAutomaker(): List<VehicleWithAutomaker>

}