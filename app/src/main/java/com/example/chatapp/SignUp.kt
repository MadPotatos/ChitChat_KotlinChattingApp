package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            signUp(email,password)
        }
    }
    private fun signUp(email:String,password:String){
        //create user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //jump to homepage
                    val intent = Intent(this@SignUp ,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp , "Oh no, something gone wrong!!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}