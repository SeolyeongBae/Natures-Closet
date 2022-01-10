package com.example.naturesCloset

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.naturesCloset.classDirectory.Colors
import com.example.naturesCloset.databinding.MyColorDataListBinding

class MyColorAdapter (private var list: MutableList<Colors>): RecyclerView.Adapter<MyColorAdapter.ColorItemViewHolder> () {

    private val context = MyApplication.ApplicationContext() as Context

    // onBindViewHolder의 역할을 대신한다, View와 데이터를 연결시키는 함수
    inner class ColorItemViewHolder(private val binding: MyColorDataListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: Colors, position: Int) {
            val(data_id, data_name, data_phonenum, color2, color3, color4) = data

            Log.d("ColorAdapter", "===== ===== ===== ===== bind ===== ===== ===== =====") //로그 출력
            Log.d("ColorAdapter", data_id+" "+data_name+" "+data_phonenum)

            var colorList : ArrayList<Colors> = arrayListOf(data)

            itemView.setOnClickListener {
                Intent(context, PostActivity::class.java).apply {
                    putExtra("data", colorList)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    Log.d("ColorAdapter", "===== ===== ===== ===== intent data ===== ===== ===== =====") //로그 출력
                }.run { context.startActivity(this) }
            }


        }

    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성 -> binding
    // ViewHolder에 쓰일 Layout을 inflate하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {

        val binding = MyColorDataListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
        //size를 측정.
    }

    // ViewHolder의 bind 메소드를 호출한다. - 데이터 묶는 함수가 실행.
    override fun onBindViewHolder(holder: MyColorAdapter.ColorItemViewHolder, position: Int) {
        Log.d("ListAdapter", "===== ===== ===== ===== onBindViewHolder ===== ===== ===== =====") // log를 남긴다.

        holder.bind(list[position], position)
        //ViewHolder의 bind method로 데이터를 넘긴다. 몇 번째 셀에 어떤 데이터를 넣을 지 관리한다.

    }



}