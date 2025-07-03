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
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,

    @SerializedName("modelo")
    val model: String,

    @SerializedName("id_montadora")
    val automakerId: Int,

    @SerializedName("motorizacao")
    val motorization: String,

    @SerializedName("ano")
    val year: Int

) : Parcelable