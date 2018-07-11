package com.example.koder.workoutapp.ViewModel

import android.app.Activity
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.koder.workoutapp.BR
import com.example.koder.workoutapp.Database.Database
import com.example.koder.workoutapp.Model.CreateWorkout
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Koder on 7/9/2018.
 */
class CreateWorkoutViewModel(private val createWorkout: CreateWorkout, private val activity: Activity): Observer, BaseObservable() {

    init {
        createWorkout.addObserver(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is String){
            if(arg == "prompt"){
                notifyPropertyChanged(BR.prompt)
            }else if(arg == "creatWorkoutInput"){
                notifyPropertyChanged(BR.createWorkoutInput)

            }else if(arg == "workoutName"){
                notifyPropertyChanged(BR.workoutName)
                notifyPropertyChanged(BR.hint)
            }else if(arg == "state"){
                notifyPropertyChanged(BR.state)
            }
        }else if(arg == "repititions"){
            notifyPropertyChanged(BR.repititions)
        }
    }

    val inputTextWatcher: TextWatcher
        get() = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                createWorkout.setInput(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }


    val repitionsTextWatcher: TextWatcher
        get() = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                try {
                    createWorkout.repititions = s.toString().toInt()
                }catch (e: Exception){
                    createWorkout.repititions = 0
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

    val prompt: String
        @Bindable get() {
            return createWorkout.prompt
        }

    val state: String
        @Bindable get() {
            if("".equals(createWorkout.workoutName)){
                return createWorkout.state
            }else {
                return createWorkout.state + " #" + (createWorkout.exercises.count()+1).toString()
            }
        }

    val workoutName: String
        @Bindable get() {
            return createWorkout.workoutName
        }

    val hint: String
        @Bindable get() {
            if("".equals(createWorkout.workoutName)){
                return "Workout Name"
            }else {
                return "Exercise Name"
            }
        }
    val createWorkoutInput: String
        @Bindable get() {
            return createWorkout.getInput()
        }
    val repititions: Int
        @Bindable get() {
            return createWorkout.repititions
        }

    fun onNextClick(view: View) {
        if(createWorkout.getInput().isNotEmpty() && "".equals(createWorkout.workoutName)){
            createWorkout.prompt = "Add Exercises"
            createWorkout.workoutName = createWorkout.getInput()
            createWorkout.state = "Add Exercise"
            Toast.makeText(view.context, "Workout name set as " + createWorkout.workoutName, Toast.LENGTH_SHORT).show()
        }else if(createWorkout.getInput().isNotEmpty()){
            createWorkout.exercises.put(createWorkout.getInput(), createWorkout.repititions)
            Toast.makeText(view.context, "Exercise added to " + createWorkout.workoutName, Toast.LENGTH_SHORT).show()
        }

        createWorkout.setInput("")
        notifyPropertyChanged(BR.createWorkoutInput)
        notifyPropertyChanged(BR.state)
    }

    fun onFinishedClick(view: View) {
        val db:Database = Database(view.context)
        if(!"".equals(createWorkout.workoutName) && createWorkout.exercises.size > 0){
            try{
                db.addWorkout(createWorkout.workoutName, createWorkout.exercises)
                activity.finish()
                Toast.makeText(view.context, "Workout succesfully created", Toast.LENGTH_SHORT).show()
            }catch(e:Exception){
                Toast.makeText(view.context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(view.context, "Workout name or exercises are missing", Toast.LENGTH_SHORT).show()
        }
    }



}