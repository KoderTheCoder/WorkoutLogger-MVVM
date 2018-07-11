package com.example.koder.workoutapp.ViewModel

import android.app.Activity
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import android.widget.Toast
import com.example.koder.workoutapp.BR
import com.example.koder.workoutapp.Database.Database
import com.example.koder.workoutapp.Model.Workout
import java.util.*
import android.os.AsyncTask
import android.os.AsyncTask.execute
import com.example.koder.workoutapp.View.FinishedWorkoutActivity
import com.example.koder.workoutapp.View.WorkoutActivity


/**
 * Created by Koder on 7/9/2018.
 */
class WorkoutViewModel(private val workout: Workout, private val activity: Activity): Observer, BaseObservable() {

    var finished:Boolean = false
    init {
        workout.addObserver(this)
        val db:Database = Database(activity)
        workout.exercises = db.getExercises(workout.getWorkoutName())
        workout.exerciseNames = db.getExerciseNames(workout.getWorkoutName())
        workout.exerciseName  = workout.exerciseNames[0]
        workout.repetitions = workout.exercises.get(workout.exerciseName).toString()
        workout.startTime = System.currentTimeMillis()

        Timers().execute()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is String){
            if(arg == "exerciseName"){
                notifyPropertyChanged(BR.exerciseName)
            }else if(arg == "repetitions"){
                notifyPropertyChanged(BR.repetitions)
            }else if(arg == "exerciseTime"){
                notifyPropertyChanged(BR.exerciseTime)
            }else if(arg == "workoutTime"){
                notifyPropertyChanged(BR.workoutTime)
            }
        }
    }

    val exerciseName: String
        @Bindable get() {
            return workout.exerciseName
        }
    val repetitions: String
        @Bindable get() {
            return workout.repetitions
        }

    val exerciseTime: String
        @Bindable get() {
            return workout.exerciseTime.toString()
        }

    val workoutTime: String
        @Bindable get() {
            return workout.workoutTime.toString()
        }


    fun onNextClick(view: View) {
        try{
            workout.currentExercise++
            workout.exerciseName  = workout.exerciseNames[workout.currentExercise]
            workout.repetitions = workout.exercises.get(workout.exerciseName).toString()
            workout.exerciseTime = 0
            notifyPropertyChanged(BR.exerciseName)
            notifyPropertyChanged(BR.repititions)
            notifyPropertyChanged(BR.exerciseTime)
        }catch (e:Exception){
            //Finished exercise
            val nextScreen = Intent(activity, FinishedWorkoutActivity::class.java)
            nextScreen.putExtra("workoutName", workout.getWorkoutName())
            nextScreen.putExtra("timeTaken", workout.getTotalTime())
            activity.startActivityForResult(nextScreen, 1)
            finished = true
            activity.finish()
        }
    }

    fun onFinishedClick(view: View) {
        finished = true
        activity.finish()
    }
    private inner class Timers : AsyncTask<Int, Int, Any>() {
        override fun doInBackground(vararg params: Int?): Any {
            timers()
            return 1
        }
    }
    fun timers(){
        var lastTimeCheck: Long = System.currentTimeMillis()
        var currentTime:Long = System.currentTimeMillis()
        while (workout.currentExercise < workout.exercises.count() && !finished){
            currentTime = System.currentTimeMillis()
            if((currentTime - lastTimeCheck) >= 1000){
                workout.exerciseTime++
                workout.workoutTime++
                notifyPropertyChanged(BR.exerciseTime)
                notifyPropertyChanged(BR.workoutTime)
                lastTimeCheck = System.currentTimeMillis()
            }
        }
    }
}