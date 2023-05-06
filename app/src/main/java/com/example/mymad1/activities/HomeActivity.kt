package com.example.mymad1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.activities.TeacherDetailsActivity
import com.example.myapplication.activities.TimetableSetup
import com.example.mymad1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var btnHomeTimetable: Button
    private lateinit var mathsBtn: Button
    private lateinit var bioBtn: Button
    private lateinit var chemBtn: Button
    private lateinit var phyBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnHomeTimetable = findViewById(R.id.btnTimetable)
        mathsBtn = findViewById(R.id.btnMaths)
        bioBtn = findViewById(R.id.btnBio)
        chemBtn = findViewById(R.id.btnChem)
        phyBtn = findViewById(R.id.btnPhy)

        btnHomeTimetable.setOnClickListener{
            //get current user (student/ teacher)
            val currentUser = FirebaseAuth.getInstance().currentUser
//            val dbStudentUser = FirebaseDatabase.getInstance().getReference("students")
            val dbTeacherUser = FirebaseDatabase.getInstance().getReference("teachers")

            dbTeacherUser.orderByChild("teacherId").equalTo(currentUser?.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        //user is a teacher
                        val intent = Intent(this@HomeActivity, TimetableSetup::class.java)
                        startActivity(intent)
                    } else {
                        //user is a student
                        val intent = Intent(this@HomeActivity, TeacherDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@HomeActivity, "Error ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        mathsBtn.setOnClickListener{
            val condition : Int = 1
            val intent = Intent(this, FetchingTeacherInfo::class.java)
            intent.putExtra("condition", condition)
            startActivity(intent)
        }

        bioBtn.setOnClickListener{
            val condition : Int = 2
            val intent = Intent(this, FetchingTeacherInfo::class.java)
            intent.putExtra("condition", condition)
            startActivity(intent)
        }

        chemBtn.setOnClickListener{
            val condition : Int = 3
            val intent = Intent(this, FetchingTeacherInfo::class.java)
            intent.putExtra("condition", condition)
            startActivity(intent)
        }

        phyBtn.setOnClickListener{
            val condition : Int = 4
            val intent = Intent(this, FetchingTeacherInfo::class.java)
            intent.putExtra("condition", condition)
            startActivity(intent)
        }
    }
}