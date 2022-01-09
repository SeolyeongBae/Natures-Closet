package com.example.naturesCloset

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import androidx.viewpager2.widget.ViewPager2
import com.example.naturesCloset.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityPostBinding
    private val colorlist: ArrayList<Colors> = ArrayList()
    lateinit var datas : ArrayList<Colors>

    lateinit var contents: String
    lateinit var hashtag: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datas = intent.getSerializableExtra("data") as ArrayList<Colors>

        val data = datas[0]

        val(data_id, data_name, data_phonenum, color2, color3, color4) = data

        Log.d("ColorAdapter", "===== ===== ===== ===== bind ===== ===== ===== =====") //로그 출력
        Log.d("ColorAdapter", data_id+" "+data_name+" "+data_phonenum)

        binding.sharePost.setOnClickListener {

            contents= binding.postContents.getText().toString()
            hashtag = binding.postHashtags.getText().toString()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }

    }



}