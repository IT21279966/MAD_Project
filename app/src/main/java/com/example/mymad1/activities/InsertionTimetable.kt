package com.example.mymad1.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.models.TimetableModel
import com.example.mymad1.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class InsertionTimetable : AppCompatActivity() {
    //variable initialization
    private lateinit var etName : EditText
    private lateinit var etSubject : EditText
    private lateinit var etDate : EditText
    private lateinit var etTime : EditText
    private lateinit var etLink : EditText
    private lateinit var btnSaveTimetable : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_timetable)

        etName = findViewById(R.id.addTeacherName)
        etSubject = findViewById(R.id.addSubject)
        etDate = findViewById(R.id.addDate)
        etTime = findViewById(R.id.addTime)
        etLink = findViewById(R.id.addLink)
        btnSaveTimetable = findViewById(R.id.btnSaveTimetable)

        etDate.setOnClickListener {
            showDatePicker()
        }

        etTime.setOnClickListener {
            showTimePicker()
        }


        dbRef = FirebaseDatabase.getInstance().getReference("Timetable")

        btnSaveTimetable.setOnClickListener{
            saveTimetableData()
        }
    }

    private fun saveTimetableData(){
        //get values
        val teacherName = etName.text.toString()
        val subjectName = etSubject.text.toString()
        val timetableDate = etDate.text.toString()
        val timetableTime = etTime.text.toString()
        val timetableLink = etLink.text.toString()

        if(teacherName.isEmpty()){
            etName.error = "Please enter teacher name"
        }
        if(subjectName.isEmpty()){
            etSubject.error = "Please enter subject name"
        }
        if(timetableDate.isEmpty()){
            etDate.error = "Please enter date"
        }
        if(timetableTime.isEmpty()){
            etTime.error = "Please enter time"
        }
        if(timetableLink.isEmpty()){
            etLink.error = "Please enter link"
        }

        val timetableCode = dbRef.push().key!!

        //pass data to variable
        val timetable = TimetableModel(timetableCode, teacherName, subjectName, timetableDate, timetableTime, timetableLink)

        //add data to database
        dbRef.child(timetableCode).setValue(timetable)
            //success listener
            .addOnCompleteListener{
                Toast.makeText(this, "Timetable inserted successfully!!", Toast.LENGTH_LONG).show()

                etName.text.clear()
                etSubject.text.clear()
                etDate.text.clear()
                etTime.text.clear()
                etLink.text.clear()
            }
            //failure listener
            .addOnFailureListener{err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun showDatePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Update the EditText field with the selected date
                etDate.setText("$year-${month+1}-$dayOfMonth")
            },
            year,
            month,
            day
        )

        // Show the date picker
        datePicker.show()
    }

    private fun showTimePicker() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Update the EditText field with the selected time
                etTime.setText("$hourOfDay:$minute")
            },
            hour,
            minute,
            true
        )

        // Show the time picker
        timePicker.show()
    }


}