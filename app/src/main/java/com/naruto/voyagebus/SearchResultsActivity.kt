package com.naruto.voyagebus

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val rvBusList = findViewById<RecyclerView>(R.id.rvBusList)
        val tvHeader = findViewById<TextView>(R.id.tvHeader)
        val tvEmptyState = findViewById<TextView>(R.id.tvEmptyState)
        rvBusList.layoutManager = LinearLayoutManager(this)

        // Get data passed from HomeActivity
        val fromCity = intent.getStringExtra("fromCity") ?: ""
        val toCity = intent.getStringExtra("toCity") ?: ""
        val travelDate = intent.getStringExtra("travelDate") ?: ""

        tvHeader.text = getString(R.string.buses_from_to, fromCity, toCity)

        // Filter buses by route using the clean BusData approach
        // We shuffle the resulting list to ensure random order every time
        val filteredList = BusData.busList.filter {
            it.from.equals(fromCity, ignoreCase = true) &&
            it.to.equals(toCity, ignoreCase = true)
        }.shuffled()

        Log.d("TOTAL_BUSES", BusData.busList.size.toString())

        if (filteredList.isEmpty()) {
            tvEmptyState.visibility = android.view.View.VISIBLE
            rvBusList.visibility = android.view.View.GONE
        } else {
            tvEmptyState.visibility = android.view.View.GONE
            rvBusList.visibility = android.view.View.VISIBLE
            rvBusList.adapter = BusAdapter(filteredList, travelDate)
        }
    }
}
