package com.example.ana.vehicleapp.ui.view

import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ana.vehicleapp.R
import com.example.ana.vehicleapp.data.model.Vehicle

class VehicleAdapter (
    private var vehicles: List<Vehicle>
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int = vehicles.size

    fun updateVehicles(newVehicles: List<Vehicle>) {
        this.vehicles = newVehicles
        notifyDataSetChanged()
    }

    class VehicleViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val modelTextView: TextView = itemView.findViewById(R.id.text_view_model)
        private val detailsTextView: TextView = itemView.findViewById(R.id.text_view_details)

        fun bind(vehicle: Vehicle) {
            modelTextView.text = vehicle.model
            detailsTextView.text = "Ano: ${vehicle.year} - Motor: ${vehicle.motorization}"
        }

    }

}