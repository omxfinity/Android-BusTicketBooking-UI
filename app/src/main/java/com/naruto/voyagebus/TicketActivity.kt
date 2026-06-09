package com.naruto.voyagebus

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val tvFromCity = findViewById<TextView>(R.id.tvFromCity)
        val tvToCity = findViewById<TextView>(R.id.tvToCity)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvTime = findViewById<TextView>(R.id.tvTime)
        val tvSeatNumber = findViewById<TextView>(R.id.tvSeatNumber)
        val tvTotalFare = findViewById<TextView>(R.id.tvTotalFare)
        val ivQrCode = findViewById<ImageView>(R.id.ivQrCode)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val tvBackToHome = findViewById<TextView>(R.id.tvBackToHome)

        // Get data from PaymentActivity
        val seats = intent.getStringArrayListExtra("selectedSeats") ?: arrayListOf()
        val totalFare = intent.getIntExtra("totalFare", 0)
        val fromCity = intent.getStringExtra("fromCity") ?: "N/A"
        val toCity = intent.getStringExtra("toCity") ?: "N/A"
        val departureTime = intent.getStringExtra("departureTime") ?: "N/A"
        val travelDate = intent.getStringExtra("travelDate") ?: "N/A"

        // Set data to views
        tvFromCity.text = fromCity.uppercase()
        tvToCity.text = toCity.uppercase()
        tvDate.text = travelDate.uppercase()
        tvTime.text = departureTime.uppercase()
        tvSeatNumber.text = seats.joinToString(", ")
        tvTotalFare.text = getString(R.string.fare_label, totalFare)

        // Generate QR code
        try {
            val qrData = "Voyage Bus Ticket\nRoute: $fromCity to $toCity\nDate: $travelDate\nTime: $departureTime\nSeats: ${seats.joinToString(", ")}\nFare: ₹$totalFare"
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 500, 500)
            ivQrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        btnShare.setOnClickListener {
            val shareText = "My Voyage Bus Ticket:\n$fromCity to $toCity\nDate: $travelDate\nTime: $departureTime\nSeats: ${seats.joinToString(", ")}"
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Ticket Via"))
        }

        tvBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
