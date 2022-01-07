package com.example.naturesCloset

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naturesCloset.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding

    companion object{
        const val TAG : String = "로그"
        fun newInstance(): HomeFragment{
            return HomeFragment()
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



}