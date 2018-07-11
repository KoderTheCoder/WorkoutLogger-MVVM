package com.example.koder.workoutapp.Database;

/**
 * Created by Koder on 7/10/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by koder on 26/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "PocketTrainer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS workouts"+
                "(id integer primary key AUTOINCREMENT, name VARCHAR UNIQUE, exercisesCount integer);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS exercises"+
                "(id integer primary key AUTOINCREMENT, name VARCHAR, repititions integer, workout_id integer,"+
                " FOREIGN KEY (workout_id) REFERENCES workouts(id))");
        Log.d("Database", "Tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addWorkout(String workoutName, HashMap<String, Integer> exercises){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", workoutName);
        db.insert("workouts", null, contentValues);
        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+ workoutName + "'", null);
        workout_id.moveToNext();

        Object[] keys = exercises.keySet().toArray();
        for (int i = 0; i < keys.length; ++i) {
            contentValues.put("name", keys[i].toString());
            contentValues.put("repititions", exercises.get(keys[i].toString()));
            contentValues.put("workout_id", workout_id.getInt(workout_id.getColumnIndex("id")));
            db.insert("exercises", null, contentValues);
            contentValues.clear();
        }
        return true;
    }
    public ArrayList<String> getWorkouts(){
        ArrayList<String> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor workout = db.rawQuery("SELECT * FROM workouts", null);
        while(workout.moveToNext()) {
            workouts.add(workout.getString(workout.getColumnIndex("name")));
        }
        return workouts;
    }
    public HashMap<String, Integer> getExercises(String workoutName){
        HashMap<String, Integer> exercises = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+workoutName+"'", null);
        workout_id.moveToNext();
        int id = workout_id.getColumnIndex("id");
        Cursor exercise = db.rawQuery("SELECT * FROM exercises WHERE workout_id = "+workout_id.getInt(id), null);
        while(exercise.moveToNext()) {
            exercises.put(exercise.getString(exercise.getColumnIndex("name")), exercise.getInt(exercise.getColumnIndex("repititions")));
        }
        return exercises;
    }

    public LinkedList<String> getExerciseNames(String workoutName){
        LinkedList<String> exerciseNames = new LinkedList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+workoutName+"'", null);
        workout_id.moveToNext();
        int id = workout_id.getColumnIndex("id");
        Cursor exercise = db.rawQuery("SELECT * FROM exercises WHERE workout_id = "+workout_id.getInt(id), null);
        while(exercise.moveToNext()) {
            exerciseNames.add(exercise.getString(exercise.getColumnIndex("name")));
        }
        return exerciseNames;
    }


    public boolean deleteWorkout(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+name+"'", null);
        workout_id.moveToNext();
        int id = workout_id.getColumnIndex("id");
        try{
            Cursor cursor1 = db.rawQuery("DELETE FROM workouts WHERE name = '"+name+"'", null);
            Cursor cursor2 = db.rawQuery("DELETE FROM exercises WHERE workout_id = "+workout_id.getInt(id), null);
            cursor2.moveToNext();
            cursor1.moveToNext();
        }catch (SQLException e){
            return false;
        }
        return true;
    }

}
