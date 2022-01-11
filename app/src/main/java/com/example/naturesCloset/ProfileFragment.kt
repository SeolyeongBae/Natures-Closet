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
import com.example.naturesCloset.classDirectory.EditResponse
import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.classDirectory.PaletteResponse
import com.example.naturesCloset.databinding.FragmentHomeBinding

import com.example.naturesCloset.databinding.ActivityMainBinding
import com.example.naturesCloset.serviceDirectory.EditProfileService
import com.example.naturesCloset.serviceDirectory.ShowMyPaletteService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class ProfileFragment : Fragment() {
    private lateinit var myColorAdapter: MyColorAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mbinding: ActivityMainBinding
    var list: ArrayList<Colors> =ArrayList()
    var share: PaletteResponse? = null
    var colorname: String = ""
    var colorlist: ArrayList<Colors> = arrayListOf<Colors>()

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
        val userProfile = requireActivity().intent!!.extras!!.get("UserData") as ArrayList<String>

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.163:80")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var showMyPaletteService: ShowMyPaletteService = retrofit.create(ShowMyPaletteService::class.java)

        showMyPaletteService.requestPalette(userProfile[0]).enqueue(object :
            Callback<PaletteResponse> {
            override fun onFailure(call: Call<PaletteResponse>, t: Throwable) {
                Log.e("SHOW", "============Show Error!==========")
            }

            override fun onResponse(
                call: Call<PaletteResponse>,
                response: Response<PaletteResponse>
            ) {
                share = response.body()
                Log.d("SHOW", "============Show Success!!==========")
                colorlist = share?.data!!
                Log.d("SHOW", colorlist.toString())

                list = colorlist
                myColorAdapter = MyColorAdapter(list, userProfile)
                binding.listViewProfile.adapter = myColorAdapter

            }
        })

        list = colorlist
        Log.d("SHOW", "==========onviewcreated start==========")
        binding.userProfileName.text = ""


        myColorAdapter = MyColorAdapter(list,  userProfile)
        binding.listViewProfile.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        binding.userProfileName.text = userProfile[0]
        var imagebytes = Base64.decode(userProfile[1],0)
        binding.profileImg.setImageBitmap(BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.size))

        Log.e("ContactsFragment", "Data List: ${list}")

        // Fragment에서 전달받은 list를 넘기면서 Adapter 생성
        binding.listViewProfile.adapter = myColorAdapter
        binding.profileImg.setOnClickListener {
            openGallery()
        }

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*/

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, 102)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        val userProfile = requireActivity().intent!!.extras!!.get("UserData") as ArrayList<String>

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
            val profileImageBase64 = Base64.encodeToString(byteArray, NO_WRAP)
            // 여기까지 인코딩 끝

            //server 통신
            //retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.249.18.163:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var editProfileService: EditProfileService = retrofit.create(EditProfileService::class.java)

            editProfileService.requestEdit(userProfile[0],profileImageBase64).enqueue(object :
                Callback<EditResponse> {
                override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("SHOW", "============Profedit Error!==========")
                }
                override fun onResponse(
                    call: Call<EditResponse>,
                    response: Response<EditResponse>
                ) {
                    Log.d("SHOW", "============Profedit Success!!==========")

                }
            })
            binding.profileImg.setImageURI(currentImageURL)

        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }




}