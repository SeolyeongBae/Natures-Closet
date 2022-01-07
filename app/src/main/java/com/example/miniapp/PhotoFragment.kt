package com.example.miniapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PhotoFragment : Fragment(), PhotoAdapter.ClickListener {


    private lateinit var adapter: PhotoAdapter
    private val photoList: ArrayList<Uri> = ArrayList()
    private val sendList: ArrayList<String> = ArrayList()
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate((R.layout.fragment_photo), container, false)

        if(arguments != null){
            val uris: ArrayList<String> = arguments?.getStringArrayList("img") as ArrayList<String>
            for(i in 0 until (uris.size)){
                photoList.add(Uri.parse(uris[i]))
                sendList.add(uris[i])
            }
        }

        initRecycleView(view)
        return view
    }



    private fun initRecycleView(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.photoView)
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.setHasFixedSize(true)
        adapter = PhotoAdapter(photoList, this, mainActivity)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        mainActivity?.let{
            val intent = Intent(it, PhotoActivity::class.java)
            intent.putExtra("photoList",sendList)
            intent.putExtra("position", position)
            it.startActivity(intent)
        }
//        Toast.makeText(mainActivity, "사진", Toast.LENGTH_SHORT).show()
    }


}