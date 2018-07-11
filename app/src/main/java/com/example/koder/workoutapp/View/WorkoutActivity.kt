package com.example.koder.workoutapp.View

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.koder.workoutapp.BR
import com.example.koder.workoutapp.Model.Workout
import com.example.koder.workoutapp.R
import com.example.koder.workoutapp.ViewModel.WorkoutViewModel

class WorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.do_workout)

        val workout = Workout(intent.getStringExtra("workoutName"))
        setTitle(intent.getStringExtra("workoutName"))

        val workoutViewModel = WorkoutViewModel(workout, this)

        /// Data-Binding
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.do_workout)
        binding.setVariable(BR.workout, workoutViewModel)
    }
}