package com.example.naturesCloset

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.naturesCloset.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val TAG: String = "LoginActivity"
    lateinit var inputEmail: String
    lateinit var inputPwd: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputEmailEdit = findViewById<TextInputEditText>(R.id.TextInputEditText_email)
        val inputPwdEdit = findViewById<TextInputEditText>(R.id.TextInputEditText_password)



        binding.loginBtn.setOnClickListener {

            inputEmail = inputEmailEdit.toString()
            inputPwd = inputPwdEdit.toString()

            //여기서부터 db랑 연결 필요

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}



