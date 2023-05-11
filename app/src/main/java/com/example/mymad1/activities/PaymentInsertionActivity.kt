package com.example.mymad1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mymad1.R
import com.example.mymad1.models.PaymentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PaymentInsertionActivity : AppCompatActivity() {
    private lateinit var etCardNumber : EditText
    private lateinit var etCardHolderName : EditText
    private lateinit var etExpireMonth : EditText
    private lateinit var etExpireYear : EditText
    private lateinit var etCvv : EditText
    private lateinit var btnSavePaymentData : Button


    private lateinit var dbRef: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_insertion)

        etCardNumber = findViewById(R.id.etCardNumber)
        etCardHolderName = findViewById(R.id.etCardHolderName)
        etExpireMonth = findViewById(R.id.etExpireMonth)
        etExpireYear = findViewById(R.id.etExpireYear )
        etCvv = findViewById(R.id.etCvv )
        btnSavePaymentData = findViewById(R.id.btnSavePayment)
        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        btnSavePaymentData.setOnClickListener {

            savePaymentData()
            val intent = Intent(this, PaymentFetchingActivity::class.java)
            startActivity(intent)
        }
    }


    private fun savePaymentData(){

//getting values
        val CardNumber = etCardNumber.text.toString()
        val CardHolderName = etCardHolderName.text.toString()
        val ExpireMonth = etExpireMonth.text.toString()
        val ExpireYear = etExpireYear.text.toString()
        val Cvv = etCvv.text.toString()

        if (CardNumber .isEmpty()){
            etCardNumber .error ="Please enter Card Number"
        }

        if (CardHolderName.isEmpty()){
            etCardHolderName.error ="Please enter Card Holder Name"
        }

        if (ExpireMonth.isEmpty()){
            etExpireMonth.error ="Please enter Expire Month"
        }

        if (ExpireYear.isEmpty()){
            etExpireYear.error ="Please enter Expire Year"
        }

        if (Cvv.isEmpty()){
            etCvv.error ="Please enter Expire Year"
        }

        val PaymentId = dbRef.push().key!!

        val Payment = PaymentModel(PaymentId,CardNumber,CardHolderName,ExpireMonth,ExpireYear,Cvv)

        dbRef.child(PaymentId).setValue(Payment)

            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etCardNumber.text.clear()
                etCardHolderName.text.clear()
                etExpireMonth.text.clear()
                etExpireYear.text.clear()
                etCvv.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()

            }

    }
}


//class PaymentInsertionActivity : AppCompatActivity() {
//    private lateinit var etCardNumber : EditText
//    private lateinit var etCardHolderName : EditText
//    private lateinit var etExpireMonth : EditText
//    private lateinit var etExpireYear : EditText
//    private lateinit var etCvv : EditText
//    private lateinit var btnSavePaymentData : Button
//
//    private lateinit var dbRef: DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_payment_insertion)
//
//        etCardNumber = findViewById(R.id.etCardNumber)
//        etCardHolderName = findViewById(R.id.etCardHolderName)
//        etExpireMonth = findViewById(R.id.etExpireMonth)
//        etExpireYear  = findViewById(R.id.etExpireYear )
//        etCvv  = findViewById(R.id.etCvv )
//        btnSavePaymentData = findViewById(R.id.btnSavePayment)
//        dbRef = FirebaseDatabase.getInstance().getReference("payments")
//
//        btnSavePaymentData.setOnClickListener {
//
//            savePaymentData()
//        }
//    }
//    private fun savePaymentData(){
//
//        //getting values
//        val CardNumber = etCardNumber.text.toString()
//        val CardHolderName = etCardHolderName.text.toString()
//        val ExpireMonth = etExpireMonth.text.toString()
//        val ExpireYear = etExpireYear.text.toString()
//        val Cvv = etCvv.text.toString()
//
//        if (CardNumber .isEmpty()){
//            etCardNumber .error ="Please enter Card Number"
//        }
//
//        if (CardHolderName.isEmpty()){
//            etCardHolderName.error ="Please enter Card Holder Name"
//        }
//
//        if (ExpireMonth.isEmpty()){
//            etExpireMonth.error ="Please enter Expire Month"
//        }
//
//        if (ExpireYear.isEmpty()){
//            etExpireYear.error ="Please enter Expire Year"
//        }
//
//        if (Cvv.isEmpty()){
//            etCvv.error ="Please enter Expire Year"
//        }
//
//        val PaymentId = dbRef.push().key!!
//
//        val Payment = PaymentModel(PaymentId,CardNumber,CardHolderName,ExpireMonth,ExpireYear,Cvv)
//
//        dbRef.child(PaymentId).setValue(Payment)
//            .addOnCompleteListener{
//                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()
//
//                etCardNumber.text.clear()
//                etCardHolderName.text.clear()
//                etExpireMonth.text.clear()
//                etExpireYear.text.clear()
//                etCvv.text.clear()
//
//            }.addOnFailureListener { err ->
//                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
//            }
//    }
//}