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
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener{
            val intent =Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            login(email,password)

        }
        binding.edtPassword.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // perform action on key press
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()

                    login(email,password)

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

    private fun login(email: String , password: String){
        //login
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //jump to homepage
                    val intent = Intent(this@Login ,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login , "User doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}