package com.example.mymad1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
import com.example.mymad1.models.EnrolModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TeacherDetailsActivity : AppCompatActivity(){
    private lateinit var tvSubjectName: TextView
    private lateinit var tvTeacherName : TextView
    private lateinit var tvClassFee : TextView
//    private lateinit var tvDate : TextView
//    private lateinit var tvTime : TextView
    private lateinit var btnEnrolSubject : Button

    private lateinit var dbRef : DatabaseReference
    private lateinit var dbAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.view_subject_info)

        initView()
        setValuesToViews()

        btnEnrolSubject = findViewById(R.id.btnEnrolSubject)

        //check if enrolled or not
        //get current userId
        val currentUser = FirebaseAuth.getInstance().currentUser
        val dbEnrollments = FirebaseDatabase.getInstance().getReference("enrollments")

        //view btn enrol according to the condition
        dbEnrollments.orderByChild("userId").equalTo(currentUser?.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //user already enrolled
                    btnEnrolSubject.visibility = View.VISIBLE
                } else {
                    btnEnrolSubject.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TeacherDetailsActivity, "Error ${error.message}", Toast.LENGTH_LONG).show()
            }
        })

        dbRef = FirebaseDatabase.getInstance().getReference("Enrollment")

        btnEnrolSubject.setOnClickListener{
            tvSubjectName = findViewById(R.id.tvSubjectName)
            tvTeacherName = findViewById(R.id.tvTeacherName)
            tvClassFee = findViewById(R.id.tvClassFee)
            enrolStudent()
        }

    }

    private fun enrolStudent(){
        //get current userId
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        //check if enrolled or not
        //get current userId
        val dbEnrollments = FirebaseDatabase.getInstance().getReference("enrollments")

        //get values
        val stuId = userId.toString()
        val subjectName = tvSubjectName.text.toString()
        val teacherName = tvTeacherName.text.toString()
        val classFee = tvClassFee.text.toString()

        dbEnrollments.orderByChild("userId").equalTo(currentUser?.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    //user already enrolled
                    Toast.makeText(this@TeacherDetailsActivity, "You are already enrolled!", Toast.LENGTH_LONG).show()
                }
                else{
                    //new enrollment
                    val enrolCode = dbRef.push().key!!

                    //pass data to variable
                    val enrol = EnrolModel(enrolCode, /*stuId,*/ subjectName, teacherName, classFee)

                    //add data to database
                    dbRef.child(enrolCode).setValue(enrol)
                        //success listener
                        .addOnCompleteListener{
                            Toast.makeText(this@TeacherDetailsActivity, "You are enrolled successfully!!", Toast.LENGTH_LONG).show()

//                            etName.text.clear()
//                            etSubject.text.clear()
//                            etDate.text.clear()
//                            etTime.text.clear()
//                            etLink.text.clear()
                        }
                        //failure listener
                        .addOnFailureListener{err->
                            Toast.makeText(this@TeacherDetailsActivity, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TeacherDetailsActivity, "Error ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initView() {
        tvSubjectName = findViewById(R.id.tvSubjectName)
        tvTeacherName = findViewById(R.id.tvTeacherName)
        tvClassFee = findViewById(R.id.tvClassFee)
//        tvDate = findViewById(R.id.tvTimetableDate)
//        tvTime = findViewById(R.id.tvTimetableTime)
    }

    private fun setValuesToViews(){
        tvSubjectName.text = intent.getStringExtra("teacherSubject")
        tvTeacherName.text = intent.getStringExtra("teacherName")
        tvClassFee.text = intent.getStringExtra("classFee")
//        tvDate.text = intent.getStringExtra("timetableDate")
//        tvTime.text = intent.getStringExtra("timetableTime")
    }
}