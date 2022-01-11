package com.example.naturesCloset

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.naturesCloset.classDirectory.Colors
import com.example.naturesCloset.classDirectory.User
import com.example.naturesCloset.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var images = ArrayList<String>()
    private val PICK_IMAGES_CODE = 0

    var dataList : ArrayList<User> = arrayListOf(
        User(id = "1", username = "홍길동", phNum = "0100101010"),
        User(id = "2", username = "김길동", phNum = "0100101010"),
        User(id = "3", username = "박길동", phNum = "0100101010"),
        User(id = "4", username = "최길동", phNum = "0100101010")
    )

    var colorList : ArrayList<Colors> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) { // 앱 최초 실행 시 수행
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val add_photo_btn = findViewById<ImageButton>(R.id.btn_add_photo)
        val wish_items = findViewById<ImageView>(R.id.wishList)

        setSupportActionBar(binding.toolbar) //커스텀한 toolbar를 액션바로 사용
        supportActionBar?.setDisplayShowTitleEnabled(false) //액션바에 표시되는 제목의 표시유무를 설정합니다. false로 해야 custom한 툴바의 이름이 화면에 보이게 됩니다.

        var userData : ArrayList<String> = intent!!.extras!!.get("LoginValue") as ArrayList<String>

        Log.d("Main","msg : "+userData[0].toString())
        Log.d("Main","msg : "+userData[1].toString())

        colorList = arrayListOf(
            Colors(palettename = "testset", color1="#ffffff", color2="#283860", color3="#283860", color4="#486088", color5="#ffffff", color6="#e8e0f0",username = userData)
        )

        bottom_nav.setOnItemSelectedListener { item ->

            when(item.itemId){
                    R.id.nav_home -> {
                        bottom_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_home)
                        add_photo_btn.visibility = View.INVISIBLE
                        binding.toolbarText.text = "My Profile"
                        binding.wishList.visibility=View.VISIBLE
                        intent.putExtra("ColorList", colorList)
                        intent.putExtra("UserData", userData)
                        changeFragment(ProfileFragment())
                    }

                    R.id.nav_contacts -> {
                        bottom_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_home)
                        add_photo_btn.visibility = View.INVISIBLE
                        binding.wishList.visibility=View.INVISIBLE
                        changeFragment(ContactsFragment())
                        binding.toolbarText.text = "Hello, " + userData[0]
                        intent.putExtra("DataList", dataList)
                    }

                    R.id.nav_photo -> {
                        bottom_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_home)
                        add_photo_btn.visibility = View.VISIBLE
                        binding.wishList.visibility=View.INVISIBLE
                        changeFragment(PaletteFragment())
                        binding.toolbarText.text = "Color your Clothes!"
                    }
                }
            true
        }

        binding.wishList.setOnClickListener {
            add_photo_btn.visibility = View.VISIBLE
            binding.wishList.visibility=View.VISIBLE
            changeFragment(WishListFragment())
            binding.toolbarText.text = "Wish lists"
        }

        bottom_nav.selectedItemId = R.id.nav_home


        // pick images clicking this button
        add_photo_btn.setOnClickListener {
            pickImagesIntent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_con, fragment).commit() //fl_con의 id를 가지는 Framelayout에 fragment 배치.
    }

    private fun pickImagesIntent(){
        val intent = Intent()
        intent.type = "image/*"
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE)
//        startActivityForResult(intent, PICK_IMAGES_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

                if (requestCode == PICK_IMAGES_CODE){
                    if(resultCode == Activity.RESULT_OK){
                        images.clear()

                        val fragmentManager: FragmentManager = supportFragmentManager
                        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                        val photoFragment = PaletteFragment()
                val bundle = Bundle()

                if(data!!.clipData != null){
                    //pick multiple images
                    val count = data.clipData!!.itemCount
                    for(i in 0 until count){
                        val imagesUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imagesUri.toString())
                    }
                    bundle.putStringArrayList("img", images)

                }
                else{
                    data?.data?.let { uri ->
                        val imageUri : Uri? = data?.data
                        if(imageUri != null){
                            images!!.add(imageUri.toString())
                        }
                    }
                    bundle.putStringArrayList("img", images)

                }

                photoFragment.arguments = bundle
                fragmentTransaction.add(R.id.fl_con, photoFragment).commit()
            }
        }
    }


}