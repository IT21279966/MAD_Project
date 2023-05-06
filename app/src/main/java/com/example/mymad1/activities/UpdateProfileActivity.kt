package com.example.mymad1.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
import com.example.mymad1.models.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class UpdateProfileActivity:AppCompatActivity() {

    private lateinit var editProfileName: TextView
    private lateinit var editProfileEmail: TextView
    private lateinit var editProfileMobileNumber: TextView




    private lateinit var btnUpdateEditProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editProfileName = findViewById(R.id.editStudentName)
        editProfileEmail = findViewById(R.id.editStudentEmail)
        editProfileMobileNumber = findViewById(R.id.editStudentMobileNumber)

        getStudentData()

        btnUpdateEditProfile = findViewById(R.id.btnUpdateEditProfile)

        btnUpdateEditProfile.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)  //change activity
            startActivity(intent)
        }

    }

    private fun getStudentData(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = FirebaseDatabase.getInstance().getReference("students/$userId")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val student = snapshot.getValue(Students::class.java)
                editProfileName.text = student?.stdName
                editProfileEmail.text = student?.stdEmail
                editProfileMobileNumber.text = student?.stdPhone
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read user data.", error.toException())
            }

        })
    }
///****************************************************************
//    private fun updateStudentData(){
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val userRef = FirebaseDatabase.getInstance().getReference("students/$userId")
//        val database = FirebaseDatabase.getInstance()
//        updateProfileName = findViewById<EditText>(R.id.editStudentName)
//        updateProfileEmail = findViewById<EditText>(R.id.editStudentEmail)
//        updateProfileMobileNumber = findViewById<EditText>(R.id.editStudentMobileNumber)

//        updateProfileName.setOnEditorActionListener {_,actionId,_->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                updateData(updateProfileName.text.toString(), database, userId)
//                true
//            }else{
//                false
//            }
//
//        }
//
//        updateProfileEmail.setOnEditorActionListener {_,actionId,_->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                updateData(updateProfileEmail.text.toString(), database, userId)
//                true
//            }else{
//                false
//            }
//
//        }

//        updateProfileMobileNumber.setOnEditorActionListener {_,actionId,_->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                updateData(updateProfileMobileNumber.text.toString(), database, userId)
//                true
//            }else{
//                false
//            }
//
//        }

 //   }

//    private fun updateData(newData: String, database: FirebaseDatabase, uid: String?) {
//        // Get a reference to the user's data in the database
//        val userRef = database.getReference("students/$uid")
//
//        // Update the data in the database
//        userRef.child("stdName").setValue(newData)
//        // Update the text view with the new data
//        updateProfileName.text = newData
//
//        userRef.child("stdEmail").setValue(newData)
//        // Update the text view with the new data
//        updateProfileEmail.text = newData
//
//        userRef.child("stdPhone").setValue(newData)
//        // Update the text view with the new data
//        updateProfileMobileNumber.text = newData
//    }
//************************************************************************************************

}