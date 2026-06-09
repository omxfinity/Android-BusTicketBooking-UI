package com.naruto.voyagebus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment

class EarnPointsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_earn_points, container, false)

        view.findViewById<View>(R.id.btnRefer).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Join Voyage Bus and travel the future! Download now: https://voyagebus.app/refer/EXPLORER123")
            }
            startActivity(Intent.createChooser(shareIntent, "Refer via"))
            // Simulate giving points for referring (normally done on server)
            addPoints(200)
            Toast.makeText(requireContext(), "Referral link shared! You'll get 200 points when they join.", Toast.LENGTH_LONG).show()
        }

        view.findViewById<View>(R.id.cardLumina).setOnClickListener {
            Toast.makeText(requireContext(), "Lumina Quiz coming soon!", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.cardSudoku).setOnClickListener {
            Toast.makeText(requireContext(), "Sudoku coming soon!", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.cardChess).setOnClickListener {
            Toast.makeText(requireContext(), "Chess coming soon!", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.cardSnake).setOnClickListener {
            Toast.makeText(requireContext(), "Snake Game coming soon!", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.btnBuyPoints).setOnClickListener {
            Toast.makeText(requireContext(), "Purchase gateway coming soon!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun addPoints(points: Int) {
        val sharedPrefs = requireContext().getSharedPreferences("VoyageBusPrefs", Context.MODE_PRIVATE)
        val currentPoints = sharedPrefs.getInt("user_points", 6524)
        sharedPrefs.edit { putInt("user_points", currentPoints + points) }
    }
}
