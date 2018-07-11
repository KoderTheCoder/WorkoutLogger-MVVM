package com.example.koder.workoutapp.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.koder.workoutapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val createWorkoutButton = findViewById<Button>(R.id.createWorkout)
        createWorkoutButton.setOnClickListener{
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        val chooseWorkoutButton = findViewById<Button>(R.id.chooseWorkout)
        chooseWorkoutButton.setOnClickListener {
            val intent = Intent(this, ChooseWorkoutActivity::class.java)
            startActivity(intent)
        }
    }
}
