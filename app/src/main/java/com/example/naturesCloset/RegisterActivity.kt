package com.example.naturesCloset

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

import com.example.naturesCloset.databinding.ActivityRegisterBinding

class RegisterActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

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
        var th: Thread = object : Thread() {
            override fun run() {
                super.run()
                val jsonOb = JSONObject()
                jsonOb.put("userid", phone)   // phone : 전화번호(아이디) 입력값
                jsonOb.put("userpw", pass) // pass  : 비밀번호 입력값
                jsonOb.put("username", uname)   // uname : 이름 입력값
                jsonOb.put("userprof", "init") //init: 기본 설정값

                // /join으로 post방식 요청을 보내기 위해 설정
                val url = URL("http://192.249.18.163/join")
                var conn: HttpURLConnection? = null
                conn = url.openConnection() as HttpURLConnection
                conn.doOutput = true
                conn.requestMethod = "POST" // POST로 요청
                conn.setRequestProperty("Connection", "Keep-Alive") // Keep-Alive : 단일 TCP 소켓을 사용해서 다수의 요청과 응답을 처리
                conn.setRequestProperty("Content-Type", "application/json") // Request Body 전달 시 json으로 서버에 전달

                val jsonStr = jsonOb.toString() // json을 string으로 변환 후 서버로 보내야됨
                val os: OutputStream = conn.getOutputStream()
                os.write(jsonStr.toByteArray(charset("UTF-8"))) // 한글 깨짐 방지
                os.flush()

                val sb = StringBuilder()
                val HttpResult = conn.getResponseCode()
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    val br = BufferedReader(
                        InputStreamReader(conn.getInputStream(), "utf-8")
                    )

                    br.close()
                    println("" + sb.toString())
                } else
                    System.out.println(conn.getResponseMessage())
                os.close()
                Log.d("json", "" + jsonStr)
            }
        }
        th.start()
    }

}
