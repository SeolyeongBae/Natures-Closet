package com.example.miniapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2


class PhotoActivity : AppCompatActivity() {

    private val viewPager: ViewPager2 by lazy { findViewById(R.id.vp2) }
    private val photoList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        intent.getStringArrayListExtra("photoList")?.let{
            for(idx in  0 until (it.size)){
                photoList.add(Uri.parse(it[idx]))
            }
        }
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = VpAdapter(photoList, this)
        viewPager.setCurrentItem(intent.getIntExtra("position", 0), false)
    }


}