package com.example.naturesCloset

import com.example.naturesCloset.serviceDirectory.LoginService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



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
            .baseUrl("http://192.249.18.163") // 주소는 본인의 서버 주소로 설정
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
                    var login = response.body()
                    //Toast.makeText(getApplicationContext(),login?.status, Toast.LENGTH_SHORT).show()
                    if (login?.status.equals("error") || login?.status.equals("false")) {
                        //Toast.makeText(getApplicationContext(),"This account not exist!", Toast.LENGTH_SHORT).show()
                        Log.d("LOGIN","============Login Failure!!==========")
                    }
                    else {
                        startActivity(intent)
                        Log.d("LOGIN","============Login Success!!==========")
                        Log.d("LOGIN","msg : "+login?.msg)
                        Log.d("LOGIN","code : "+login?.data)
                    }

                }
        })
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}






