package com.example.naturesCloset

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class WishListAdapter(private val photoList: ArrayList<Uri>, private val listener: ClickListener,
                      val context: Context) : RecyclerView.Adapter<WishListAdapter.PhotoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = photoList[position]
//        Glide.with(context).load(item).override(100, 100).into(holder.photoImg)
        Glide.with(context).load(item).into(holder.photoImg)


        holder.photoImg.setBackgroundResource(R.drawable.round_img)
        holder.photoImg.clipToOutline = true

//        holder.itemView.setOnClickListener{
//            clickListener.onItemClick(photoList[position])
//        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class PhotoViewHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        val photoImg : ImageView = v.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface ClickListener {
        fun onItemClick(position: Int)
    }
//
//    fun setItemClickListener(ClickListener: ClickListener){
//        this.clickListener = ClickListener
//    }

}