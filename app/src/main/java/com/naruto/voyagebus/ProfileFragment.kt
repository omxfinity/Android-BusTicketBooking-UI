package com.naruto.voyagebus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        
        // Get user data from SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences("VoyageBusPrefs", Context.MODE_PRIVATE)
        val name = sharedPrefs.getString("user_name", "Explorer")
        val email = sharedPrefs.getString("user_email", "explorer@voyage.com")
        
        tvUserName.text = name
        tvUserEmail.text = email

        btnLogout.setOnClickListener {
            // Clear login state
            sharedPrefs.edit().putBoolean("is_logged_in", false).apply()
            
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            
            // Navigate to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        
        return view
    }
}
