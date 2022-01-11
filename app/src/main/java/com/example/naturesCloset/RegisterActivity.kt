package com.example.naturesCloset

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.naturesCloset.classDirectory.FeedResponse
import com.example.naturesCloset.classDirectory.JoinResponse
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

import com.example.naturesCloset.databinding.ActivityRegisterBinding
import com.example.naturesCloset.serviceDirectory.GetFeedService
import com.example.naturesCloset.serviceDirectory.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class RegisterActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    var share: JoinResponse?= null

    lateinit var phone: String
    lateinit var pass: String
    lateinit var uname: String

    val TAG: String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {

            Log.d("SENTI", "onClick")
            phone= binding.TextInputEditTextEmail.getText().toString()
            pass = binding.TextInputEditTextPassword.getText().toString()
            uname  = binding.TextInputEditTextUsername.getText().toString()

            Log.d("The email value", phone)

            getJoin()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getJoin() { // "가입" 버튼 클릭 시
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.163:80")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var registerService: RegisterService = retrofit.create(RegisterService::class.java)

        registerService.requestLogin(phone, pass, uname).enqueue(object :
            Callback<JoinResponse> {
            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.e("SHOW", "============Show Error!==========")
            }

            override fun onResponse(
                call: Call<JoinResponse>,
                response: Response<JoinResponse>
            ) {
                Log.d("SHOW", "============Show Success!!==========")
            }
        })
    }

}
