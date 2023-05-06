package com.example.mymad1.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
import com.example.mymad1.models.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewProfileActivity: AppCompatActivity() {


    private lateinit var viewProfileName: TextView
    private lateinit var viewProfileEmail: TextView
    private lateinit var viewProfileMobileNumber: TextView
    private  lateinit var viewProfileHeaderEmail : TextView
    private  lateinit var viewProfileHeaderName : TextView

    private lateinit var btnUpdateProfile : Button
    private lateinit var btnDeleteProfile : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_profile)

        viewProfileName = findViewById(R.id.viewProfileName)
        viewProfileEmail = findViewById(R.id.viewProfileEmail)
        viewProfileMobileNumber = findViewById(R.id.viewProfileMobileNumber)
        viewProfileHeaderEmail = findViewById(R.id.viewProfileHeaderEmail)
        viewProfileHeaderName = findViewById(R.id.viewProfileHeaderName)


        getStudentData()

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile)

        btnUpdateProfile.setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)  //change activity
            startActivity(intent)
        }

        btnDeleteProfile.setOnClickListener {
            val intent = Intent(this, DeleteProfileActivity::class.java)  //change activity
            startActivity(intent)
        }


    }

    private fun getStudentData(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = FirebaseDatabase.getInstance().getReference("students/$userId")

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val student = snapshot.getValue(Students::class.java)
                viewProfileName.text = student?.stdName
                viewProfileEmail.text = student?.stdEmail
                viewProfileMobileNumber.text = student?.stdPhone
                viewProfileHeaderName.text = student?.stdName
                viewProfileHeaderEmail.text = student?.stdEmail
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read user data.", error.toException())
            }

        })
    }
}















//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import com.example.mymad1.R
//import com.example.mymad1.activities.DeleteProfileActivity
//import com.example.mymad1.activities.UpdateProfileActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//
//class ViewProfileActivity : AppCompatActivity() {
//
//    //private lateinit var btnDeleteProfile: Button
//    private lateinit var btnUpdateProfile: Button
//    private lateinit var auth : FirebaseAuth
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.view_profile)
//
//        //Check User exists or not
//        auth =FirebaseAuth.getInstance()
//
//        if(auth.currentUser == null){
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//        }
//
//        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
//
//        //btnDeleteProfile = findViewById(R.id.btnDeleteProfile)
//        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)
//
////        btnDeleteProfile.setOnClickListener {
////            val intent = Intent(this, DeleteProfileActivity::class.java)  //change activity
////            startActivity(intent)
////        }
//
//        btnUpdateProfile.setOnClickListener {
//            val intent = Intent(this, UpdateProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//
//
//        val myButton = findViewById<Button>(R.id.btnDeleteProfile)
//
//// Set an onClickListener on the button
//        myButton.setOnClickListener {
//            // Call your function here
//            showDeleteAlert()
//        }
//
//    }
//
//
//
//    private fun showDeleteAlert(){
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage("Are you sure you want to delete profile?")
//            .setTitle("Delete Profile")
//            .setPositiveButton("Yes") { dialog, which ->
//
//                val database = FirebaseDatabase.getInstance()
//                val nodeRef = database.getReference("Profile")
//
//                // Use the removeValue() method to delete the node from the database
//                nodeRef.removeValue()
//                    .addOnSuccessListener {
//                        // Display a success message to the user
//                        Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener { error ->
//                        // Display an error message to the user
//                        Toast.makeText(this, "Error deleting Profile: ${error.message}", Toast.LENGTH_SHORT).show()
//                    }
//            }
//            .setNegativeButton("No") { dialog, which ->
//                // Do nothing
//                dialog.cancel()
//            }
//
//        val dialog = builder.create()
//        dialog.show()
//    }
//
//
//
//}

