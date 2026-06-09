package com.naruto.voyagebus

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BusAdapter(private val buses: List<Bus>, private val travelDate: String) :
    RecyclerView.Adapter<BusAdapter.BusViewHolder>() {

    class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBusName: TextView = itemView.findViewById(R.id.tvBusName)
        val tvRoute: TextView = itemView.findViewById(R.id.tvRoute)
        val tvDeparture: TextView = itemView.findViewById(R.id.tvDeparture)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvSeats: TextView = itemView.findViewById(R.id.tvSeats)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bus, parent, false)
        return BusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val bus = buses[position]
        val context = holder.itemView.context
        
        holder.tvBusName.text = bus.busName
        holder.tvRoute.text = context.getString(R.string.route_display, bus.from, bus.to)
        holder.tvDeparture.text = context.getString(R.string.departure_label, bus.departureTime)
        holder.tvPrice.text = context.getString(R.string.fare_label, bus.fare)
        holder.tvSeats.text = context.getString(R.string.seats_left, bus.availableSeats)
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra("busName", bus.busName)
                putExtra("fare", bus.fare)
                putExtra("fromCity", bus.from)
                putExtra("toCity", bus.to)
                putExtra("departureTime", bus.departureTime)
                putExtra("travelDate", travelDate)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = buses.size
}
