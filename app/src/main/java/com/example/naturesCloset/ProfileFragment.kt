package com.example.naturesCloset

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturesCloset.databinding.FragmentHomeBinding

import com.example.naturesCloset.databinding.ActivityMainBinding


class ProfileFragment : Fragment(){
    private lateinit var myColorAdapter: MyColorAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mbinding: ActivityMainBinding

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

        var list: ArrayList<Colors> =
            requireActivity().intent!!.extras!!.get("ColorList") as ArrayList<Colors>
        //list를 전달받는 과정이다.

        myColorAdapter = MyColorAdapter(list)
        binding.listViewProfile.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        Log.e("ContactsFragment", "Data List: ${list}")

        // Fragment에서 전달받은 list를 넘기면서 Adapter 생성
        binding.listViewProfile.adapter = myColorAdapter


    }


}