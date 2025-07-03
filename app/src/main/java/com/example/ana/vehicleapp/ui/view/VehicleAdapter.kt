package com.example.ana.vehicleapp.ui.view

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ana.vehicleapp.R
import com.example.ana.vehicleapp.data.model.VehicleWithAutomaker

class VehicleAdapter(
    private var vehicles: List<VehicleWithAutomaker>
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    var longClickedPosition: Int = -1
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicleWithAutomaker = vehicles[position]
        holder.bind(vehicleWithAutomaker)

        holder.itemView.setOnLongClickListener {
            longClickedPosition = holder.adapterPosition
            false
        }
    }

    override fun getItemCount(): Int = vehicles.size

    fun updateVehicles(newVehicles: List<VehicleWithAutomaker>) {
        this.vehicles = newVehicles
        notifyDataSetChanged()
    }

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        private val modelTextView: TextView = itemView.findViewById(R.id.text_view_model)
        private val automakerTextView: TextView = itemView.findViewById(R.id.text_view_automaker)
        private val detailsTextView: TextView = itemView.findViewById(R.id.text_view_details)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(vehicleWithAutomaker: VehicleWithAutomaker) {
            val vehicle = vehicleWithAutomaker.vehicle
            modelTextView.text = vehicle.model
            automakerTextView.text = vehicleWithAutomaker.automakerName
            detailsTextView.text = "Ano: ${vehicle.year} - Motor: ${vehicle.motorization}"
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val inflater = MenuInflater(v?.context)
            inflater.inflate(R.menu.vehicle_context_menu, menu)
        }
    }
}