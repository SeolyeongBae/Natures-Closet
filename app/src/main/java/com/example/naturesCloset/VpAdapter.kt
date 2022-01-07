package com.example.naturesCloset

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView


class VpHolder(v : View) : RecyclerView.ViewHolder(v) {

    var photoImg : PhotoView

    init {
        photoImg = v.findViewById(R.id.vp_img)
    }


}

class VpAdapter(private val photoList: ArrayList<Uri>, val context: Context) : RecyclerView.Adapter<VpHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.vp_photo_item, parent, false)
        return VpHolder(view)
    }

    override fun onBindViewHolder(holder: VpHolder, position: Int) {
        val item = photoList[position]
//        Glide.with(context).load(item).override(100, 100).into(holder.photoImg)
        Glide.with(context).load(item).into(holder.photoImg)


//        holder.photoImg.clipToOutline = true

    }

    override fun getItemCount(): Int {
        return photoList.size
    }

}