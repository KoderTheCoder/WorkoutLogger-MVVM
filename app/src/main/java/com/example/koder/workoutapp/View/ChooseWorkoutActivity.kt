package com.example.koder.workoutapp.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import com.example.koder.workoutapp.R
import com.example.koder.workoutapp.Database.Database
import com.example.koder.workoutapp.ListAdapter
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.TextView
import com.example.koder.workoutapp.Model.Workout
import android.content.Intent
import android.widget.AdapterView
import android.widget.Toast






/**
 * Created by Koder on 7/9/2018.
 */
class ChooseWorkoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_workout)

        setTitle("Choose Workout")
        val adapter: ListAdapter
        val list: ListView
        val db = Database(this)
        val workouts = db.workouts

        //build adapter, give it items
        adapter = ListAdapter(this, workouts)



        list = findViewById<View>(R.id.workoutList) as ListView
        list.setAdapter(adapter)

        adapter.notifyDataSetChanged()

        //click and hold, deletes item from list
        list.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, _, i, _ ->
            //get selected item
            val selectedItem = adapterView.getItemAtPosition(i) as String
            //get adapter this list is using (cast it to appropriate type)
            val itemAdapter = adapterView.adapter as ListAdapter
            //remove item from adapters item list
            if (db.deleteWorkout(selectedItem)) {
                Toast.makeText(this@ChooseWorkoutActivity, selectedItem + " deleted", Toast.LENGTH_SHORT).show()
                itemAdapter.remove(selectedItem)
            } else {
                Toast.makeText(this@ChooseWorkoutActivity, "error deleting " + selectedItem, Toast.LENGTH_SHORT).show()
            }
            //make adapter notify layout needs re-rendering
            itemAdapter.notifyDataSetChanged()

            false
        }

        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            val selectedItem = adapterView.getItemAtPosition(i) as String
            //Toast.makeText(ChooseWorkout.this, selectedItem, Toast.LENGTH_SHORT).show();

            val nextScreen = Intent(this@ChooseWorkoutActivity, WorkoutActivity::class.java)
            nextScreen.putExtra("workoutName", selectedItem)
            startActivityForResult(nextScreen, 1)
            finish()
        }
    }
}

/*

MyAdapter adapter;
        ListView list;
        final Database db = new Database(this);
        ArrayList<String> workouts = db.getWorkouts();

        //build adapter, give it items
        adapter = new MyAdapter(this, workouts);

        //get list from layout and give it the adapter
        list = (ListView)findViewById(R.id.workoutList);
        list.setAdapter(adapter);
        //let layout know it should re-render the list
        adapter.notifyDataSetChanged();
 */