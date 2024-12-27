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

class signup : AppCompatActivity() {

    private lateinit var edt_name: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var bt_signup: Button
    private lateinit var bt_login: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        edt_name = findViewById(R.id.edt_name)
        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)

        bt_login = findViewById(R.id.bt_login)
        bt_login.setOnClickListener(){
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        bt_signup = findViewById(R.id.bt_signup)
        bt_signup.setOnClickListener(){
            var email = edt_email.text.toString()
            var password = edt_password.text.toString()

            signup(email, password)
        }
    }

    private fun signup(email : String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    val intent = Intent(this, MenuPAge::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
                }

            }
    }

}