package com.example.koder.workoutapp.View

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.koder.workoutapp.BR
import com.example.koder.workoutapp.Model.CreateWorkout
import com.example.koder.workoutapp.R
import com.example.koder.workoutapp.ViewModel.CreateWorkoutViewModel
import kotlinx.android.synthetic.main.create_workout.*

/**
 * Created by Koder on 7/9/2018.
 */
class CreateWorkoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_workout)

        val createWorkout = CreateWorkout("")

        createWorkout.prompt = "Create Workout"
        createWorkout.state = "Set Workout Name"
        createWorkout.repititions = 0

        val createWorkoutViewModel = CreateWorkoutViewModel(createWorkout, this)




        /// Data-Binding
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.create_workout)
        binding.setVariable(BR.createWorkout, createWorkoutViewModel)

    }
}