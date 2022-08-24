package com.example.chatapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef :DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            signUp(name,email,password)
        }
        binding.edtPassword.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // perform action on key press
                    val name = binding.edtName.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()

                    signUp(name,email,password)

                    // hide soft keyboard programmatically
                    hideSoftKeyboard()

                    // clear focus and hide cursor from edit text
                    binding.edtPassword.clearFocus()
                    binding.edtPassword.isCursorVisible = false

                    return true
                }
                return false
            }
        })
    }
    private fun signUp(name:String, email:String,password:String){
        //create user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //jump to homepage
                        addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp ,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp , "Oh no, something gone wrong!!",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String  ) {
        mDbRef =FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}