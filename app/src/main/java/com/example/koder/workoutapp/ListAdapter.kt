package com.example.koder.workoutapp

/**
 * Created by Koder on 7/10/2018.
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

//Adapter takes data and tries to map it to a layout
class ListAdapter(context: Context, values: ArrayList<String>)//context(current activity)
//then which row layout to use
//which part of the row layout can we change the text
//and the values to put in that part we want to change
    : ArrayAdapter<String>(context, R.layout.list_view_layout, R.id.rowText, values) {

    //this is called for each row of our list rendered to the screen
    //so each view it builds is a single row
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent);

        //converts a xml layout file into a collection of class objects
        val inflater = LayoutInflater.from(context)

        //get row layout and build it into a View object
        //parent is which group of existing viewgroups should we add this
        //and false, we do not want to automatically add it to this parent group
        val view = inflater.inflate(R.layout.list_view_layout, parent, false)

        //get code reference to rowText
        val rowText = view.findViewById<View>(R.id.rowText) as TextView

        //gets current item from our arrayList
        val text = getItem(position)

        //change the rowText's text
        rowText.text = text


        return view
    }
}