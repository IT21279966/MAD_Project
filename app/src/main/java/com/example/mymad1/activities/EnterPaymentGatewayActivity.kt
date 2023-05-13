package com.example.mymad1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mymad1.R


class EnterPaymentGatewayActivity : AppCompatActivity() {

    private lateinit var btnInsertionPayment : Button
//    private lateinit var btnFetchingPayment : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_payment_gateway)

//        btnFetchingPayment = findViewById(R.id.btnFetchPayment)
        btnInsertionPayment = findViewById(R.id.btnInsertPayment)

//        btnFetchingPayment.setOnClickListener {
//            val intent = Intent(this, PaymentFetchingActivity::class.java)
//            startActivity(intent)
//        }

        btnInsertionPayment.setOnClickListener {
            val intent = Intent(this, PaymentInsertionActivity::class.java)
            startActivity(intent)
        }
    }
}