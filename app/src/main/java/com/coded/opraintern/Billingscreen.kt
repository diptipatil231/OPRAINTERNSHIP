package com.coded.opraintern

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Billingscreen : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    lateinit var user_id: TextInputEditText
    lateinit var quantity: TextInputEditText
    lateinit var fat : TextInputEditText
    lateinit var snf : TextInputEditText
    lateinit var generate_bill: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billingscreen)

        user_id = findViewById(R.id.user_id)
        quantity = findViewById(R.id.quantity)
        fat = findViewById(R.id.fat)
        snf = findViewById(R.id.snf)

        generate_bill = findViewById(R.id.generate_bill)

        generate_bill.setOnClickListener {
            val username : String = user_id.text.toString()
            val quantity : String = quantity.text.toString()
            val fat : String = fat.text.toString()
            val snf : String = snf.text.toString()

            generateBill(username, quantity, fat, snf)
        }
    }

    fun generateBill(username: String, quantity: String, fat: String, snf: String) {

        /*db.collection("billing").document().set(info)*/

        val intent = Intent(this, BillReview::class.java)
        intent.putExtra("username", "$username")
        intent.putExtra("quantity", "$quantity")
        intent.putExtra("fat", "$fat")
        intent.putExtra("snf", "$snf")
        startActivity(intent)
    }
}