package com.example.koder.workoutapp.Model

import android.app.Application
import java.util.*

/**
 * Created by Koder on 7/9/2018.
 */
class CreateWorkout(private var input: String): Observable() {

    public val exercises = HashMap<String, Int>()

    var state: String = ""
        set(value) {
            field = value
            setChangedAndNotify("state")
        }

    var prompt: String = ""
    set(value) {
        field = value
        setChangedAndNotify("prompt")
    }

    var workoutName: String = ""
        set(value) {
            field = value
            setChangedAndNotify("workoutName")
        }

    var repititions: Int = 0
        set(value) {
            field = value
            setChangedAndNotify("repitions")
        }



    fun setInput(input: String){
        this.input = input
    }

    fun getInput(): String{
        return input
    }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}