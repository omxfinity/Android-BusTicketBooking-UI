package com.naruto.voyagebus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val tvSeats = findViewById<TextView>(R.id.tvSeats)
        val tvFare = findViewById<TextView>(R.id.tvFare)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnScanPay = findViewById<View>(R.id.btnScanPay)
        val rgPaymentMethods = findViewById<RadioGroup>(R.id.rgPaymentMethods)
        val btnBack = findViewById<View>(R.id.btnBack)
        val cbRedeemPoints = findViewById<CheckBox>(R.id.cbRedeemPoints)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val seats = intent.getStringArrayListExtra("selectedSeats") ?: arrayListOf()
        var totalFare = intent.getIntExtra("totalFare", 0)
        val originalFare = totalFare
        val fromCity = intent.getStringExtra("fromCity")
        val toCity = intent.getStringExtra("toCity")
        val departureTime = intent.getStringExtra("departureTime")
        val travelDate = intent.getStringExtra("travelDate")

        tvSeats.text = getString(R.string.seats_selected_label, seats.joinToString(", "))
        tvFare.text = getString(R.string.total_fare_label, totalFare)

        cbRedeemPoints.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                totalFare -= 50
                Toast.makeText(this, getString(R.string.points_applied), Toast.LENGTH_SHORT).show()
            } else {
                totalFare = originalFare
            }
            tvFare.text = getString(R.string.total_fare_label, totalFare)
        }

        rgPaymentMethods.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            if (radioButton != null) {
                Toast.makeText(this, getString(R.string.proceeding_with_method, radioButton.text), Toast.LENGTH_SHORT).show()
            }
        }

        btnScanPay.setOnClickListener {
            Toast.makeText(this, getString(R.string.opening_scanner), Toast.LENGTH_SHORT).show()
        }

        btnConfirm.setOnClickListener {
            val checkedId = rgPaymentMethods.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, getString(R.string.select_payment_method), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.payment_processing, totalFare), Toast.LENGTH_LONG).show()

                if (cbRedeemPoints.isChecked) {
                    val sharedPrefs = getSharedPreferences("VoyageBusPrefs", MODE_PRIVATE)
                    val currentPoints = sharedPrefs.getInt("user_points", 6524)
                    sharedPrefs.edit { putInt("user_points", (currentPoints - 500).coerceAtLeast(0)) }
                } else {
                    val sharedPrefs = getSharedPreferences("VoyageBusPrefs", MODE_PRIVATE)
                    val currentPoints = sharedPrefs.getInt("user_points", 6524)
                    sharedPrefs.edit { putInt("user_points", currentPoints + 50) }
                }

                btnConfirm.isEnabled = false
                btnConfirm.postDelayed({
                    Toast.makeText(this, getString(R.string.payment_success), Toast.LENGTH_LONG).show()
                    val intent = Intent(this, TicketActivity::class.java).apply {
                        putStringArrayListExtra("selectedSeats", seats)
                        putExtra("totalFare", totalFare)
                        putExtra("fromCity", fromCity)
                        putExtra("toCity", toCity)
                        putExtra("departureTime", departureTime)
                        putExtra("travelDate", travelDate)
                    }
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }
}
