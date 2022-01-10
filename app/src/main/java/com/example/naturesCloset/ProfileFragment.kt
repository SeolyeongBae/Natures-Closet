package com.example.naturesCloset

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturesCloset.classDirectory.Colors
import com.example.naturesCloset.databinding.FragmentHomeBinding

import com.example.naturesCloset.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class ProfileFragment : Fragment() {
    private lateinit var myColorAdapter: MyColorAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mbinding: ActivityMainBinding
    var list: ArrayList<Colors> =ArrayList()

    lateinit var mainActivity: MainActivity

    companion object{
        const val TAG : String = "로그"
        fun newInstance(): ProfileFragment{
            return ProfileFragment()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment - onCreate called")

    }

    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment - onAttach() called")
        mainActivity = context as MainActivity
    }

    //뷰 생성
    // fragment와 레이아웃 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreateView() called")
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = requireActivity().intent!!.extras!!.get("ColorList") as ArrayList<Colors>
        //list를 전달받는 과정이다.

        //data from server

        binding.userProfileName.text = ""

        myColorAdapter = MyColorAdapter(list)
        binding.listViewProfile.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        Log.e("ContactsFragment", "Data List: ${list}")

        // Fragment에서 전달받은 list를 넘기면서 Adapter 생성
        binding.listViewProfile.adapter = myColorAdapter
        binding.profileImg.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, 102)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == 102 && resultCode == Activity.RESULT_OK){
            var currentImageURL = intent?.data
            // Base64 인코딩부분
            val ins: InputStream? = currentImageURL?.let {
                MyApplication.ApplicationContext().contentResolver.openInputStream(
                    it
                )
            }

            val img: Bitmap = BitmapFactory.decodeStream(ins)
            ins?.close()
            val resized = Bitmap.createScaledBitmap(img, 256, 256, true)
            val byteArrayOutputStream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val outStream = ByteArrayOutputStream()
            val res: Resources = resources
            val profileImageBase64 = Base64.encodeToString(byteArray, NO_WRAP)
            // 여기까지 인코딩 끝

            //server 통신
            val jsonOb = JSONObject()
            jsonOb.put("userprof", profileImageBase64)

            val url = URL("http://192.249.18.163/profedit")
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

            // 이미지 뷰에 선택한 이미지 출력
            binding.profileImg.setImageURI(currentImageURL)
            try {
                //이미지 선택 후 처리
            }catch (e: Exception){
                e.printStackTrace()
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }




}