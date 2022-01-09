package com.example.naturesCloset

import LoginService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.naturesCloset.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URISyntaxException
import java.net.URL
import android.text.Editable

import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    lateinit var phone: String
    lateinit var pass: String
    lateinit var uname: String

    val TAG: String = "LoginActivity"
    var login: LoginResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {

            Log.d("The email value", phone)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.165") // 주소는 본인의 서버 주소로 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService: LoginService = retrofit.create(LoginService::class.java)

        binding.loginBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            Log.d("SENTI", "onClick")
            phone= binding.TextInputEditTextEmail.getText().toString()
            pass = binding.TextInputEditTextPassword.getText().toString()

            loginService.requestLogin(phone,pass).enqueue(object: Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LOGIN","============Login Error!==========")
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    login = response.body()
                    Log.d("LOGIN","msg : "+login?.msg)
                    Log.d("LOGIN","code : "+login?.code)
                    Log.d("LOGIN","============Login Success!!==========")

                    startActivity(intent)
                }
        })

        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}






