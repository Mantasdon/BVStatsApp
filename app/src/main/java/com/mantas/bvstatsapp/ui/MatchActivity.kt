package com.mantas.bvstatsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.mantas.bvstatsapp.R
import com.mantas.bvstatsapp.ui.MatchAdapter
import com.mantas.bvstatsapp.entity.Match

class MatchActivity : AppCompatActivity() {

    private lateinit var matchRecyclerView: RecyclerView
    private lateinit var matchAdapter: MatchAdapter
    private val matchList = mutableListOf<Match>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        // Setup RecyclerView
        matchRecyclerView = findViewById(R.id.matchRecyclerView)
        matchRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns

        // Initialize adapter
        matchAdapter = MatchAdapter(matchList)
        matchRecyclerView.adapter = matchAdapter

        // Load initial matches (stub data)
        loadMatches()

        // Setup "Create Match" button
        val createMatchButton: Button = findViewById(R.id.createMatchButton)
        createMatchButton.setOnClickListener {
            showCreateMatchDialog()
        }
    }

    private fun loadMatches() {
        // Stub: Add some placeholder matches
        matchList.add(Match("Match 1"))
        matchList.add(Match("Match 2"))
        matchAdapter.notifyDataSetChanged()
    }

    private fun showCreateMatchDialog() {
        // Inflate the custom dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_match, null)

        val matchNameInput = dialogView.findViewById<TextInputEditText>(R.id.matchNameInput)

        // Create AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create Match")
            .setView(dialogView)
            .setPositiveButton("Create") { _, _ ->
                val matchName = matchNameInput.text.toString()
                if (matchName.isNotBlank()) {
                    addMatch(matchName)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun addMatch(matchName: String) {
        val newMatch = Match(matchName)
        matchList.add(newMatch)
        matchAdapter.notifyDataSetChanged()
    }
}
