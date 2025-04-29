package com.mantas.bvstatsapp.ui

import androidx.lifecycle.lifecycleScope

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mantas.bvstatsapp.R
import com.mantas.bvstatsapp.dao.LineDao
import com.mantas.bvstatsapp.database.AppDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class StatsActivity : AppCompatActivity() {
    private lateinit var LinesDao: LineDao
    private var currentType: String = "" // Tracks the current "Defended" or "Kills" type
    private var currentAction: String = "" // Tracks the selected action (e.g., Spike, Shot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // Initialize the database and LineDao
        val database = AppDatabase.getInstance(this) // Ensure you have this method in AppDatabase
        LinesDao = database.lineDao()

        // Find views
        val spikeButton = findViewById<Button>(R.id.btnSpike)
        val shotButton = findViewById<Button>(R.id.btnShot)
        val blockedButton = findViewById<Button>(R.id.btnBlocked)
        val pokeyButton = findViewById<Button>(R.id.btnPokey)
        val defendedButton = findViewById<Button>(R.id.btnDefended)
        val killsButton = findViewById<Button>(R.id.btnKills)
        val drawingView = findViewById<StatsDrawingView>(R.id.drawing_view)
        val resetButton = findViewById<Button>(R.id.btnReset)

        // Set type based on "Defended" or "Kills" button presses
        defendedButton.setOnClickListener {
            currentType = "Defended"
            handleOptionClick(drawingView)
        }
        killsButton.setOnClickListener {
            currentType = "Kills"
            handleOptionClick(drawingView)
        }
        resetButton.setOnClickListener {
            deleteAllLines() // Use a coroutine to delete all lines
            val drawingView = findViewById<StatsDrawingView>(R.id.drawing_view)
            drawingView.clearLines()
        }

        // Handle specific action buttons
        spikeButton.setOnClickListener {
            currentAction = "Spike"
            handleOptionClick(drawingView)
        }
        shotButton.setOnClickListener {
            currentAction = "Shot"
            handleOptionClick(drawingView)
        }
        blockedButton.setOnClickListener {
            currentAction = "Blocked"
            handleOptionClick(drawingView)
        }
        pokeyButton.setOnClickListener {
            currentAction = "Pokey"
            handleOptionClick(drawingView)
        }
    }

    private fun handleOptionClick(drawingView: StatsDrawingView) {
        drawingView.setDrawingEnabled(currentAction, currentType) // Pass current action and type to drawing view
        println("Selected option: $currentAction, Type: $currentType")
    }

    private fun deleteAllLines() {
        // Run the database operation on a background thread using a coroutine
        lifecycleScope.launch(Dispatchers.IO) {
            LinesDao.deleteAll()
            println("All lines deleted.")
        }
    }
}
