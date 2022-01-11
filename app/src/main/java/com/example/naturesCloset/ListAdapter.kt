package com.example.naturesCloset

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.naturesCloset.ContactsFragment.Companion.TAG
import com.example.naturesCloset.classDirectory.User
import com.example.naturesCloset.databinding.ContactsDataListBinding
import androidx.viewbinding.ViewBinding;
import com.example.naturesCloset.databinding.MyColorDataListBinding
import java.util.Random


class ListAdapter (private var list: MutableList<User>): RecyclerView.Adapter<ListAdapter.ListItemViewHolder> () {

// onBindViewHolder의 역할을 대신한다, View와 데이터를 연결시키는 함수
    inner class ListItemViewHolder(v : View, private val binding: ContactsDataListBinding): RecyclerView.ViewHolder(v), View.OnClickListener{

    val likebtn : LottieAnimationView = binding.heartBtn


    //val bal6 = v.findViewById(R.id.bal6) as ImageView
    //val v_id = v.getResources().getResourceName(bal1.getId()).toString()

    fun bind(data: User, position: Int) {

            val(username, color1, color2, color3, color4, color5, color6, content, hashtag, admin) = data

            Log.d("ListAdapter", "===== ===== ===== ===== bind ===== ===== ===== =====") //로그 출력
            Log.d("ListAdapter", color1+" "+color4+" "+color6)

            val rnd = Random()
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            var count : Int = 0

            binding.contents.text = content
            binding.Username.text = username
            binding.hashtag.text = hashtag //이게 여기에 있어서 매번 main에 호출될때마다 불려나오는 것 같다

            count = binding.likeNum.text.toString().toInt()
            //val clothes = v_id.split("/")[1]
            //Log.d("ListAdapter", clothes+" ")

            //bal6.setColorFilter(Color.parseColor(color6), PorterDuff.Mode.MULTIPLY)

            binding.heartBtn.setOnClickListener(View.OnClickListener {
                if(!list[position].admin){
                    val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(500)
                    animator.addUpdateListener {
                        binding.heartBtn.progress= it.animatedValue as Float
                    }
                    animator.start()
                    list[position].admin = true

                    count++
                    binding.likeNum.text=count.toString()
                    Log.d(TAG, "Mainactivity - onClickbutton() Called")
                }
                else{
                    val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500)
                    animator.addUpdateListener {
                        binding.heartBtn.progress= it.animatedValue as Float
                    }
                    animator.start()
                    list[position].admin = false

                    count--
                    binding.likeNum.text=count.toString()

                }
            })

        }

    override fun onClick(v: View?) {
        val position: Int = adapterPosition
        if(position != RecyclerView.NO_POSITION) {
        }
    }

    }

    interface ClickListener {
        fun onItemClick(position: Int)
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성 -> binding
    // ViewHolder에 쓰일 Layout을 inflate하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder { //ListItemViewHolder 형식으로 리턴.

        val binding = ContactsDataListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_data_list, parent, false)

        return ListItemViewHolder(view, binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
        //size를 측정.
    }

    // ViewHolder의 bind 메소드를 호출한다. - 데이터 묶는 함수가 실행.
    override fun onBindViewHolder(holder: ListAdapter.ListItemViewHolder, position: Int) {
        Log.d("ListAdapter", "===== ===== ===== ===== onBindViewHolder ===== ===== ===== =====") // log를 남긴다.
        holder.bind(list[position], position)
        Log.d("Position", "$position") //로그 출력
        //ViewHolder의 bind method로 데이터를 넘긴다. 몇 번째 셀에 어떤 데이터를 넣을 지 관리한다.

        if(list[position].admin){
            val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(100)
            animator.addUpdateListener {
                holder.likebtn.progress= it.animatedValue as Float
            }
        }
        else{
            val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(100)
            animator.addUpdateListener {
                holder.likebtn.progress= it.animatedValue as Float
            }
        }


    }

    interface ItemClickListener{
        fun onClick(view: View,position: Int)
    }
    //를릭 리스너
    private lateinit var itemClickListner: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }




}
