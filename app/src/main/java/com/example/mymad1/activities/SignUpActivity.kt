package com.example.mymad1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.mymad1.R
import com.example.mymad1.databinding.ActivitySignUpBinding
import com.example.mymad1.models.Students
import com.google.android.material.textfield.TextInputLayout

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        //Assign IDs to variables
        val signUpStdName : EditText = findViewById(R.id.signUpStdName)
        val signUpStdEmail : EditText = findViewById(R.id.signUpStdEmail)
        val signUpStdPhone : EditText = findViewById(R.id.signUpStdPhone)
        val signUpStdPassword : EditText = findViewById(R.id.signUpStdPassword)
        val signUpStdConfiPassword : EditText = findViewById(R.id.signUpStdConfiPassword)
        val siginUpStdPasswordLayout : TextInputLayout = findViewById(R.id.siginUpStdPasswordLayout)
        val siginUpStdConfiPasswordLayout : TextInputLayout = findViewById(R.id.siginUpStdConfiPasswordLayout)
        val signUpStdBtn : Button = findViewById(R.id.btnsignUpStd)
        val signUpStdProgressbar : ProgressBar = findViewById(R.id.signUpProgressbar)

        val signInText : TextView = findViewById(R.id.signInTextStd)

        signInText.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        signUpStdBtn.setOnClickListener {
            val stdName = signUpStdName.text.toString()
            val stdEmail = signUpStdEmail.text.toString()
            val stdPhone = signUpStdPhone.text.toString()
            val stdPassword = signUpStdPassword.text.toString()
            val stdConfirPassword = signUpStdConfiPassword.text.toString()

            signUpStdProgressbar.visibility = View.VISIBLE
            siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = true
            siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if(stdName.isEmpty() || stdEmail.isEmpty() || stdPhone.isEmpty() || stdPassword.isEmpty() || stdConfirPassword.isEmpty()){

                if(stdName.isEmpty()){
                    signUpStdName.error = "Enter your name"
                }

                if(stdEmail.isEmpty()){
                    signUpStdEmail.error = "Enter your email"
                }

                if(stdPhone.isEmpty()){
                    signUpStdPhone.error = "Enter your mobile number"
                }

                if(stdPassword.isEmpty()){
                    siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpStdPassword.error = "Enter your password"
                }

                if(stdConfirPassword.isEmpty()){
                    siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpStdConfiPassword.error = "Confirm your password"

                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                signUpStdProgressbar.visibility = View.GONE

            }else if(!stdEmail.matches(emailPattern.toRegex())){
                signUpStdProgressbar.visibility = View.GONE
                signUpStdEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if(stdPhone.length != 10){
                signUpStdProgressbar.visibility = View.GONE
                signUpStdPhone.error = "Enter valid mobile number"
                Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show()

            }else if(stdPassword.length<6){
                siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpStdProgressbar.visibility = View.GONE
                signUpStdPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT).show()

            }else if(stdPassword != stdConfirPassword){
                siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpStdProgressbar.visibility = View.GONE
                signUpStdConfiPassword.error = "password not matched"
                Toast.makeText(this, "password not matched", Toast.LENGTH_SHORT).show()

            }else{
                auth.createUserWithEmailAndPassword(stdEmail, stdPassword).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("students").child(auth.currentUser!!.uid)
                        val students : Students = Students(stdName, stdEmail,stdPhone, auth.currentUser!!.uid)

                        databaseRef.setValue(students).addOnCompleteListener{
                            if(it.isSuccessful){
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}