package com.example.naturesCloset

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
import java.net.URISyntaxException


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val TAG: String = "LoginActivity"
    var mSocket = IO.socket("http://192.249.18.93:80")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            mSocket.connect()
            Log.d("Connected", "OK")
            if (mSocket.connect().connected()) {
                Toast.makeText(this.applicationContext, "connection success!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: URISyntaxException) {
            Log.d("ERR", e.toString())
        }
        mSocket.on(Socket.EVENT_CONNECT, onConnect)

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    val onConnect = Emitter.Listener {
        mSocket.emit("emitReceive","OK!")

    }

}





