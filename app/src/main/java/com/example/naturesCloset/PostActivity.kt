package com.example.naturesCloset

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.example.naturesCloset.classDirectory.Colors
import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.databinding.ActivityPostBinding
import com.example.naturesCloset.serviceDirectory.LoginService
import com.example.naturesCloset.serviceDirectory.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityPostBinding
    private val colorlist: ArrayList<Colors> = ArrayList()
    lateinit var datas : ArrayList<Colors>
    var share: LoginResponse? = null

    lateinit var contents: String
    lateinit var hashtag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datas = intent.getSerializableExtra("data") as ArrayList<Colors>

        val data = datas[0]

        val(col1, col2, col3, col4, col5, col6) = data

        Log.d("ColorAdapter", "===== ===== ===== ===== get data! ===== ===== ===== =====") //로그 출력

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.163") // 주소는 본인의 서버 주소로 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var shareService: ShareService = retrofit.create(ShareService::class.java)

        binding.sharePost.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            contents= binding.postContents.getText().toString()
            hashtag = binding.postHashtags.getText().toString()

            shareService.requestLogin("testname", col1, col2, col3, col4, col5, col6, contents,hashtag).enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("POST","============Post Error!==========")
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    share = response.body()
                    Log.d("LOGIN","============Login Success!!==========")
                    startActivity(intent)
                }
            })


        }
    }

}