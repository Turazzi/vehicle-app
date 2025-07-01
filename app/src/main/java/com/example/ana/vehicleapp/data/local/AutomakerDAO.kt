package com.example.ana.vehicleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ana.vehicleapp.data.model.Automaker

@Dao
interface AutomakerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(automakers: List<Automaker>)

    @Query("SELECT * FROM automakers")
    suspend fun getAll(): List<Automaker>

}