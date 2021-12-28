package com.coded.opraintern

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class MainActivity : AppCompatActivity() {

    lateinit var  login_btn : Button
    lateinit var signup_btn: Button
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        signup_btn = findViewById(R.id.signup_btn)
        login_btn = findViewById(R.id.login_btn)

        signup_btn.setOnClickListener {

            val email : String = email.text.toString()
            val password : String = password.text.toString()

            signUp(auth, email, password)
        }

        login_btn.setOnClickListener {

            val email : String = email.text.toString()
            val password : String = password.text.toString()

            signIn(auth, email, password)
        }

    }

    private fun signUp(auth: FirebaseAuth, username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    Log.d("Login Status", "Signup successfull")
                    val user = auth.currentUser
                }else{
                    Log.d("Login Status", "createUserWithEmail:Failed")
                    Toast.makeText(baseContext, "Signup failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn(auth: FirebaseAuth, username: String, password: String){
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Signed In Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Billingscreen::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Sign in failed",Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun createBilling(view: View) {
        val intent = Intent(this, Billingscreen::class.java)
        startActivity(intent)
    }



    public override fun onStart() {
        super.onStart()

        var currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, Billingscreen::class.java)
            startActivity(intent)
        }
    }

}