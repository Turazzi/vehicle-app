package com.example.ana.vehicleapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "vehicles",
    foreignKeys = [ForeignKey (
        entity = Automaker::class,
        parentColumns = ["id"],
        childColumns = ["automakerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Vehicle (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("model")
    val model: String,

    @SerializedName("automakerId")
    val automakerId: Int,

    @SerializedName("motorization")
    val motorization: String,

    @SerializedName("year")
    val year: Int

) : Parcelable