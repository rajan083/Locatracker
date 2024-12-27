package com.example.locatracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var bt_login: Button
    private lateinit var bt_signup: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)

        bt_signup = findViewById(R.id.bt_signup)
        bt_signup.setOnClickListener(){
            startActivity(Intent(this, signup::class.java))
        }

        bt_login = findViewById(R.id.bt_login)
        bt_login.setOnClickListener(){
            var email = edt_email.text.toString()
            var password = edt_password.text.toString()

            login(email, password)
        }
    }

    private fun login(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MenuPAge::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "User does not Exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
}