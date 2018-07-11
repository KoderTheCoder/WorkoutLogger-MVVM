package com.example.koder.workoutapp.Model

import android.app.Application
import java.util.*

/**
 * Created by Koder on 7/9/2018.
 */
class Workout(private val workoutName:String): Observable() {

    public var exercises = HashMap<String, Int>()
    public var exerciseNames:LinkedList<String> = LinkedList()
    public var currentExercise:Int = 0

    public var startTime:Long = 0

    var exerciseName: String = ""
        set(value) {
            field = value
            setChangedAndNotify("exerciseName")
        }

    var exerciseTime: Int = 0
        set(value) {
            field = value
            setChangedAndNotify("exerciseTime")
        }

    var workoutTime: Int = 0
        set(value) {
            field = value
            setChangedAndNotify("workoutTime")
        }

    var repetitions: String = ""
        set(value) {
            field = value
            setChangedAndNotify("repetitions")
        }

    fun getWorkoutName(): String{
        return this.workoutName
    }

    fun getTotalTime(): String{
        return ((System.currentTimeMillis()/1000) - startTime/1000).toString()
    }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}