package com.example.mymad1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mymad1.R
//import com.example.mymad1.models.Students
import com.example.mymad1.models.Teachers
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class TeacherSignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_sign_up)

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

        val signUpSubject : EditText = findViewById(R.id.teacherSubject)
        val signUpFee : EditText = findViewById(R.id.teacherSubjectFee)

        val signInText : TextView = findViewById(R.id.signInTextStd)

        signInText.setOnClickListener{
            val intent = Intent(this, TeacherSignInActivity::class.java)
            startActivity(intent)
        }

        signUpStdBtn.setOnClickListener {
            val Name = signUpStdName.text.toString()
            val Email = signUpStdEmail.text.toString()
            val Phone = signUpStdPhone.text.toString()
            val Password = signUpStdPassword.text.toString()
            val ConfirPassword = signUpStdConfiPassword.text.toString()

            val Subject = signUpSubject.text.toString()
            val Fee = signUpFee.text.toString()

            signUpStdProgressbar.visibility = View.VISIBLE
            siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = true
            siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if(Name.isEmpty() || Email.isEmpty() || Phone.isEmpty() || Password.isEmpty() || Fee.isEmpty()|| Subject.isEmpty() || ConfirPassword.isEmpty()){

                if(Name.isEmpty()){
                    signUpStdName.error = "Enter your name"
                }

                if(Email.isEmpty()){
                    signUpStdEmail.error = "Enter your email"
                }

                if(Phone.isEmpty()){
                    signUpStdPhone.error = "Enter your mobile number"
                }

                if(Password.isEmpty()){
                    siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpStdPassword.error = "Enter your password"
                }

                if(ConfirPassword.isEmpty()){
                    siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpStdConfiPassword.error = "Confirm your password"

                }

                if(Subject.isEmpty()){
                    signUpStdPhone.error = "Enter your subject"
                }

                if(Fee.isEmpty()){
                    signUpStdPhone.error = "Enter subject fee"
                }

                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                signUpStdProgressbar.visibility = View.GONE

            }else if(!Email.matches(emailPattern.toRegex())){
                signUpStdProgressbar.visibility = View.GONE
                signUpStdEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if(Phone.length != 10){
                signUpStdProgressbar.visibility = View.GONE
                signUpStdPhone.error = "Enter valid mobile number"
                Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show()

            }else if(Password.length<6){
                siginUpStdPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpStdProgressbar.visibility = View.GONE
                signUpStdPassword.error = "password must be more than 6 characters"
                Toast.makeText(this, "password must be more than 6 characters", Toast.LENGTH_SHORT).show()

            }else if(Password != ConfirPassword){
                siginUpStdConfiPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpStdProgressbar.visibility = View.GONE
                signUpStdConfiPassword.error = "password not matched"
                Toast.makeText(this, "password not matched", Toast.LENGTH_SHORT).show()

            }else{
                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("teachers").child(auth.currentUser!!.uid)
                        val teachers : Teachers = Teachers(Name, Email,Phone, Subject, Fee, auth.currentUser!!.uid)

                        databaseRef.setValue(teachers).addOnCompleteListener{
                            if(it.isSuccessful){
                                val intent = Intent(this, TeacherSignInActivity::class.java)
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