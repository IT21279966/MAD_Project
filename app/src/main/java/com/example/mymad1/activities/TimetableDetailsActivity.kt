package com.example.mymad1.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
import com.example.mymad1.models.TimetableModel
import com.google.firebase.database.FirebaseDatabase

class TimetableDetailsActivity : AppCompatActivity(){
    private lateinit var tvCode: TextView
    private lateinit var tvName : TextView
    private lateinit var tvSubject : TextView
    private lateinit var tvDate : TextView
    private lateinit var tvTime : TextView
    private lateinit var tvLink : TextView
    private lateinit var btnUpdateTimetable : Button
    private lateinit var btnDeleteTimetable : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_timetable)

        initView()
        setValuesToViews()

        btnUpdateTimetable.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("timetableCode").toString(),
                intent.getStringExtra("teacherName").toString()
            )
        }

        btnDeleteTimetable.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("timetableCode").toString()
            )
        }
    }

    private fun deleteRecord(
        code: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Timetable").child(code)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Timetable data deleted!!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingTimetable::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            error ->
                Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvCode = findViewById(R.id.tvTimetableCode)
        tvName = findViewById(R.id.tvTeacherName)
        tvSubject = findViewById(R.id.tvSubjectName)
        tvDate = findViewById(R.id.tvTimetableDate)
        tvTime = findViewById(R.id.tvTimetableTime)
        tvLink = findViewById(R.id.tvTimetableLink)

        btnUpdateTimetable = findViewById(R.id.btnUpdate)
        btnDeleteTimetable = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvCode.text = intent.getStringExtra("timetableCode")
        tvName.text = intent.getStringExtra("teacherName")
        tvSubject.text = intent.getStringExtra("subjectName")
        tvDate.text = intent.getStringExtra("timetableDate")
        tvTime.text = intent.getStringExtra("timetableTime")
        tvLink.text = intent.getStringExtra("timetableLink")
    }

    private fun openUpdateDialog(
        timetableCode: String,
        teacherName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_timetable, null)

        mDialog.setView(mDialogView)

        val etTeacherName = mDialogView.findViewById<EditText>(R.id.etTeacherName)
        val etSubjectName = mDialogView.findViewById<EditText>(R.id.etSubject)
        val etDate = mDialogView.findViewById<EditText>(R.id.etDate)
        val etTime= mDialogView.findViewById<EditText>(R.id.etTime)
        val etLink= mDialogView.findViewById<EditText>(R.id.etLink)

        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)

        etTeacherName.setText(intent.getStringExtra("teacherName").toString())
        etSubjectName.setText(intent.getStringExtra("subjectName").toString())
        etDate.setText(intent.getStringExtra("timetableDate").toString())
        etTime.setText(intent.getStringExtra("timetableTime").toString())
        etLink.setText(intent.getStringExtra("timetableLink").toString())

        mDialog.setTitle("Updating $teacherName record!!")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updateTimetableData(
                timetableCode,
                etTeacherName.text.toString(),
                etSubjectName.text.toString(),
                etDate.text.toString(),
                etTime.text.toString(),
                etLink.text.toString(),
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //set updated data to textviews
            tvName.text = etTeacherName.text.toString()
            tvSubject.text = etSubjectName.text.toString()
            tvDate.text = etDate.text.toString()
            tvTime.text = etTime.text.toString()
            tvLink.text = etLink.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateTimetableData(
        code:String,
        name:String,
        subject:String,
        date:String,
        time:String,
        link:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Timetable").child(code)
        val timetableInfo = TimetableModel(code, name, subject, date, time, link)
        dbRef.setValue(timetableInfo)
    }
}