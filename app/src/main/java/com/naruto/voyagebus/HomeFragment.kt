package com.naruto.voyagebus

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val etFromCity = view.findViewById<AutoCompleteTextView>(R.id.etFromCity)
        val etToCity = view.findViewById<AutoCompleteTextView>(R.id.etToCity)
        val etTravelDate = view.findViewById<EditText>(R.id.etTravelDate)
        val btnFindBuses = view.findViewById<Button>(R.id.btnFindBuses)
        val tvPointsValue = view.findViewById<TextView>(R.id.tvPointsValue)
        val tvGreeting = view.findViewById<TextView>(R.id.tvGreeting)

        // Load points and name from SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences("VoyageBusPrefs", android.content.Context.MODE_PRIVATE)
        val currentPoints = sharedPrefs.getInt("user_points", 6524)
        val fullName = sharedPrefs.getString("user_name", "Explorer")
        val firstName = fullName?.split(" ")?.get(0) ?: "Explorer"
        
        tvPointsValue.text = String.format(Locale.getDefault(), "%,d", currentPoints)
        tvGreeting.text = getString(R.string.hello_user, firstName)

        view.findViewById<View>(R.id.btnRedeem).setOnClickListener {
            // Navigate to EarnPointsFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EarnPointsFragment())
                .addToBackStack(null)
                .commit()
        }

        // Setup AutoComplete for cities
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, BusData.cities)
        etFromCity.setAdapter(adapter)
        etToCity.setAdapter(adapter)

        // Date picker for Travel Date
        etTravelDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    etTravelDate.setText(getString(R.string.date_format, d, m + 1, y))
                },
                year,
                month,
                day,
            )
            datePicker.show()
        }

        // Find Buses button
        btnFindBuses.setOnClickListener {
            val fromCity = etFromCity.text.toString().trim()
            val toCity = etToCity.text.toString().trim()
            val travelDate = etTravelDate.text.toString().trim()

            if (fromCity.isEmpty() || toCity.isEmpty() || travelDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), SearchResultsActivity::class.java)
                intent.putExtra("fromCity", fromCity)
                intent.putExtra("toCity", toCity)
                intent.putExtra("travelDate", travelDate)
                startActivity(intent)
            }
        }

        return view
    }
}
