package com.coded.opraintern

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BillReview : AppCompatActivity() {

    lateinit var username_tv : TextView
    lateinit var race_tv : TextView
    lateinit var quantity_tv : TextView
    lateinit var fat_tv : TextView
    lateinit var snf_tv : TextView
    lateinit var total_bill : TextView
    lateinit var confirm_bill : Button

    var fat_rate : Double = 0.0
    var snf_rate : Double = 0.0
    var Total_Bill : Double = 0.0
    var Milk_Rate : Double = 0.0

    val db = Firebase.firestore

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_review)

        username_tv = findViewById(R.id.username_tv)
        race_tv = findViewById(R.id.race_tv)
        quantity_tv = findViewById(R.id.quantity_tv)
        fat_tv = findViewById(R.id.fat_tv)
        snf_tv = findViewById(R.id.snf_tv)
        total_bill = findViewById(R.id.total_bill)
        confirm_bill = findViewById(R.id.confirm_bill)

        val race = "cow"

        val docRef = db.collection("pricing").document(race)

        docRef.get()
            .addOnCompleteListener{document ->

                val username = intent.getStringExtra("username").toString()
                val quantity = intent.getStringExtra("quantity").toString()
                val fat = intent.getStringExtra("fat").toString()
                val snf = intent.getStringExtra("snf").toString()

                if(document != null){
                    var a = document.result.get("fat")
                    var b = document.result.get("snf")
                    var c = document.result.get("milk")

                    /*Toast.makeText(this,"${document.result.get("snf")}",Toast.LENGTH_SHORT).show()*/
                    preview(username, race, quantity, fat, snf, a, b, c)

                }else{
                    Toast.makeText(this,"No such document",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {e ->
                Toast.makeText(this,"e",Toast.LENGTH_SHORT).show()
            }

        Toast.makeText(this,"${fat_rate}",Toast.LENGTH_SHORT).show()

        confirm_bill.setOnClickListener {
            val docRef = db.collection("pricing").document(race)
            docRef.get()
                .addOnCompleteListener{document ->
                    if(document != null){
                        val username = intent.getStringExtra("username").toString()
                        val quantity = intent.getStringExtra("quantity").toString()
                        val fat = intent.getStringExtra("fat").toString()
                        val snf = intent.getStringExtra("snf").toString()

                        if(document != null) {
                            var a = document.result.get("fat")
                            var b = document.result.get("snf")
                            var c = document.result.get("milk")
                            /*Toast.makeText(this,"${document.result.get("snf")}",Toast.LENGTH_SHORT).show()*/
                            generateBill(username, race, quantity, fat, snf, a, b, c)

                        }
                    }else{
                        Toast.makeText(this,"No such document",Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {e ->
                    Toast.makeText(this,"e",Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun generateBill(
        username: String,
        race: String,
        quantity: String,
        fat: String,
        snf: String,
        fat_rate: Any?,
        snf_rate: Any?,
        Milk_Rate: Any?
    ) {
        val total_fat = fat.toDouble()*fat_rate.toString().toDouble()* quantity.toDouble()
        val total_snf =  snf.toDouble()*snf_rate.toString().toDouble()* quantity.toDouble()

        Total_Bill = quantity.toDouble()*Milk_Rate.toString().toDouble() + total_fat + total_snf

        val info = BillInfo(username,"bull",quantity,fat,snf, fat_rate as String,
            snf_rate as String, Milk_Rate as String, Total_Bill
        )
        db.collection("billing").document().set(info)
    }

    @SuppressLint("SetTextI18n")
    fun preview(
        username: String,
        race: String,
        quantity: String,
        fat: String,
        snf: String,
        fat_rate: Any?,
        snf_rate: Any?,
        Milk_Rate: Any?
    ) {
        username_tv.text = username
        race_tv.text = race
        quantity_tv.text =  "$quantity    $Milk_Rate Rs/ltr    Total : ${Milk_Rate.toString().toDouble()* quantity.toDouble()}"
        fat_tv.text = "$fat    $fat_rate Rs/ltr    Total : ${fat.toDouble()*fat_rate.toString().toDouble()* quantity.toDouble()}"
        snf_tv.text = "$snf    $snf_rate Rs/ltr    Total : ${snf.toDouble()*snf_rate.toString().toDouble()* quantity.toDouble()}"

        val total_fat = fat.toDouble()*fat_rate.toString().toDouble()* quantity.toDouble()
        val total_snf =  snf.toDouble()*snf_rate.toString().toDouble()* quantity.toDouble()

        Total_Bill = quantity.toDouble()*Milk_Rate.toString().toDouble() + total_fat + total_snf

        total_bill.text = Total_Bill.toString()
    }
}