package com.example.mymad1.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
import com.example.mymad1.models.Students
import com.example.mymad1.models.TeacherCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CheckTeacherCode: AppCompatActivity() {

    private lateinit var btnCodeConfirm: Button

    private lateinit var enterCode :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_confirmation_code)


        //val userId = FirebaseAuth.getInstance()
        //val userRef = FirebaseDatabase.getInstance().getReference("teachercode/code")

        val codeRef = FirebaseDatabase.getInstance().getReference("teachercode")
        //val editCode : EditText = findViewById(R.id.enterCode)



        btnCodeConfirm = findViewById(R.id.btn_code_confirm)

        enterCode = findViewById<EditText>(R.id.enterCode)
        //enterCode.toString()
        val typeCode = enterCode.text.toString()

        btnCodeConfirm.setOnClickListener {
            val intent = Intent(this, TeacherSignUpActivity::class.java)
            startActivity(intent)

//            val code = enterCode.text.toString().trim()
//            val isCode = codeRef.orderByChild("code").equalTo(code)

//           isCode.addListenerForSingleValueEvent(object : ValueEventListener{
//               override fun onDataChange(snapshot: DataSnapshot) {
//                    if(codeRef.toString() == typeCode){
//                            val intent = Intent(this@CheckTeacherCode, TeacherSignUpActivity::class.java)
//                            startActivity(intent)
//
//                    } else{
//                        Toast.makeText(this@CheckTeacherCode,"Incorrect Code. Try again!",Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@CheckTeacherCode, "Error ${error.message}", Toast.LENGTH_LONG).show()
//                }
//            })





//            val intent = Intent(this, TeacherSignUpActivity::class.java)
//            startActivity(intent)
//            if(codeRef == enterCode){
//                val intent = Intent(this, SignUpActivity::class.java)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this,"Incorrect Code. Try again!",Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, CheckTeacherCode::class.java)
//                startActivity(intent)
//            }

        }
    }}




