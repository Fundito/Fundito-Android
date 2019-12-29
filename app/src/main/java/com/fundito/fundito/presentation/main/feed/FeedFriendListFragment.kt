package com.fundito.fundito.presentation.main.feed

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.data.model.FriendFunding
import kotlinx.android.synthetic.main.fragment_feed_friend_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendListFragment : Fragment() {

    private lateinit var friendFundingListadapter : FriendFundingListAdapter
    private lateinit var friendFundingRecyclerView: RecyclerView

    lateinit var friendFundingListData : ArrayList<FriendFunding>

    companion object {
        fun newInstance() = FeedFriendListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_feed_friend_list, container, false)
        return view
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



//        ServiceImpl.serviceImpl.getBestSelling().enqueue(
//            object : Callback<GetSellingObjects> {
//                override fun onFailure(call: Call<GetSellingObjects>, t: Throwable) {
//
//                }
//
//                override fun onResponse(
//                    call: Call<GetSellingObjects>,
//                    response: Response<GetSellingObjects>
//                ) {
//                    if (response.isSuccessful) {
//                        bestSellingData = response.body()!!.data!!
//
//                    }
//                }
//            }
//        )

        view?.let { view ->
            // 리사이클러뷰 어댑터 생성
            friendFundingListadapter = FriendFundingListAdapter(activity?.baseContext!!)

            // 리사이클러뷰 생성
            friendFundingRecyclerView = view.findViewById(R.id.friendFundngRecyclerView)

            // 리사이클러뷰 레이아웃매니저 설정
            friendFundingRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)


            // 리사이클러뷰 어댑터 설정
            friendFundingRecyclerView.adapter = friendFundingListadapter


            // 리사이클러뷰 데이터 설정
            friendFundingListadapter.data = listOf(
                FriendFunding(
                    profileImg = R.drawable.profile1,
                    profileName = "포크tfv포크",
                    fundingNumber = 13
                ),
                FriendFunding(
                    profileImg = R.drawable.profile2,
                    profileName = "진수",
                    fundingNumber = 12
                ),
                FriendFunding(
                    profileImg = R.drawable.profile3,
                    profileName = "영우공쥬",
                    fundingNumber = 12
                ),
                FriendFunding(
                    profileImg = R.drawable.profile4,
                    profileName = "망원피바지",
                    fundingNumber = 12
                ),
                FriendFunding(
                    profileImg = R.drawable.profile5,
                    profileName = "민쥬찡",
                    fundingNumber = 12
                ),
                FriendFunding(
                    profileImg = R.drawable.profile6,
                    profileName = "꼰대시끼",
                    fundingNumber = 12
                )
            ) // listOf

            friendFundingListadapter.notifyDataSetChanged()

        }
    }


} // end class

