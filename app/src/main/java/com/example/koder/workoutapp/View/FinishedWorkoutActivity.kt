package com.example.koder.workoutapp.View

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.koder.workoutapp.R
import org.w3c.dom.Text

class FinishedWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finished_workout)

        var workoutName:TextView = findViewById(R.id.finishedWorkoutHeader)
        var timeTaken:TextView = findViewById(R.id.timeTaken)

        workoutName.setText(intent.getStringExtra("workoutName") + " Workout")
        timeTaken.setText(intent.getStringExtra("timeTaken") + "(s)")

        findViewById<Button>(R.id.home_button).setOnClickListener {
            finish()
        }
    }
}