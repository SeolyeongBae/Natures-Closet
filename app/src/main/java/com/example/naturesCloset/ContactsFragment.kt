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
import com.example.naturesCloset.classDirectory.FeedResponse
import com.example.naturesCloset.classDirectory.User
import com.example.naturesCloset.databinding.ContactsDataListBinding
import com.example.naturesCloset.databinding.FragContactsBinding
import com.example.naturesCloset.serviceDirectory.GetFeedService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ContactsFragment : Fragment(){

    private lateinit var listAdapter: ListAdapter
    private val sendList: ArrayList<String> = ArrayList()
    private lateinit var hbinding: ContactsDataListBinding
    var list: ArrayList<User> = ArrayList()
    var share: FeedResponse?= null
    var dataList: ArrayList<User> = arrayListOf()

    private var _binding: FragContactsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG: String = "로그"
        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ContactsFragment - onCreate called")
    }

    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ContactsFragment - onAttach() called")
    }


    //뷰 생성
    // fragment와 레이아웃 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.frag_contacts, container, false)
        _binding = FragContactsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userProfile = requireActivity().intent!!.extras!!.get("UserData") as ArrayList<String>

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.163:80")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var getFeedService: GetFeedService = retrofit.create(GetFeedService::class.java)

        getFeedService.requestFeed(userProfile[0]).enqueue(object :
            Callback<FeedResponse> {
            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                Log.e("SHOW", "============Show Error!==========")
            }

            override fun onResponse(
                call: Call<FeedResponse>,
                response: Response<FeedResponse>
            ) {
                share = response.body()
                Log.d("SHOW", "============Show Success!!==========")
                dataList = share?.data!!
                Log.d("SHOW", dataList.toString())
                //async 처리
                list = dataList
                listAdapter = ListAdapter(list)
                binding.listView.adapter = listAdapter

            }
        })

        list = dataList
        //list를 전달받는 과정이다.

        listAdapter = ListAdapter(list)
        binding.listView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        Log.e("ContactsFragment", "Data List: ${list}")

        // Fragment에서 전달받은 list를 넘기면서 Adapter 생성
        binding.listView.adapter = listAdapter

    }


    }


