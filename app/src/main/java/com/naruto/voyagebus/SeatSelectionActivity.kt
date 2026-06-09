package com.naruto.voyagebus

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SeatSelectionActivity : AppCompatActivity() {
    private val selectedSeats = mutableListOf<String>()
    private var seatPrice = 350 // Default price

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        val busName = intent.getStringExtra("busName") ?: "Bus"
        seatPrice = intent.getIntExtra("fare", 350)
        val fromCity = intent.getStringExtra("fromCity")
        val toCity = intent.getStringExtra("toCity")
        val departureTime = intent.getStringExtra("departureTime")
        val travelDate = intent.getStringExtra("travelDate")

        val tvHeader = findViewById<TextView>(R.id.tvHeader)
        tvHeader.text = getString(R.string.select_seats_with_bus, busName)
        val seatGrid = findViewById<GridLayout>(R.id.seatGrid)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        val tvTotalFare = findViewById<TextView>(R.id.tvTotalFare)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        tvSummary.text = getString(R.string.seats_count_summary, 0)
        tvTotalFare.text = getString(R.string.total_fare_label, 0)

        val seatSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics,
        ).toInt()
        val margin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics,
        ).toInt()

        for (i in 1..20) {
            val seatBtn = Button(this)
            val seatName = "A$i"
            seatBtn.text = seatName
            seatBtn.textSize = 12f
            
            val params = GridLayout.LayoutParams()
            params.width = seatSize
            params.height = seatSize
            params.setMargins(margin, margin, margin, margin)
            seatBtn.layoutParams = params
            
            seatBtn.setBackgroundResource(R.drawable.seat_available)
            seatBtn.setOnClickListener {
                if (selectedSeats.contains(seatName)) {
                    selectedSeats.remove(seatName)
                    seatBtn.setBackgroundResource(R.drawable.seat_available)
                } else {
                    selectedSeats.add(seatName)
                    seatBtn.setBackgroundResource(R.drawable.seat_selected)
                }
                tvSummary.text = getString(R.string.seats_count_summary, selectedSeats.size)
                tvTotalFare.text = getString(R.string.total_fare_label, selectedSeats.size * seatPrice)
            }
            seatGrid.addView(seatBtn)
        }

        btnContinue.setOnClickListener {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, PaymentActivity::class.java).apply {
                    putStringArrayListExtra("selectedSeats", ArrayList(selectedSeats))
                    putExtra("totalFare", selectedSeats.size * seatPrice)
                    putExtra("fromCity", fromCity)
                    putExtra("toCity", toCity)
                    putExtra("departureTime", departureTime)
                    putExtra("travelDate", travelDate)
                }
                startActivity(intent)
            }
        }
    }
}
