package com.fundito.fundito.presentation.main.feed

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.database.SearchItem
import com.fundito.fundito.data.model.FriendFunding
import com.fundito.fundito.presentation.charge.ChargeActivity
import com.fundito.fundito.presentation.main.status.FundingOnGoingAdapter
import com.fundito.fundito.presentation.search.SearchRecentAdapter
import com.fundito.fundito.presentation.store.StoreDetailActivity
import kotlinx.android.synthetic.main.fragment_feed_friend_list.*

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendListFragment : Fragment() {

    private lateinit var friendFundingListadapter: FriendFundingListAdapter
    private lateinit var friendFundingRecyclerView: RecyclerView

    lateinit var friendFundingListData: ArrayList<FriendFunding>

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


                friendFundingListadapter = FriendFundingListAdapter(activity?.baseContext!!)

                friendFundingRecyclerView = view.findViewById(R.id.friendFundingRecyclerView)

                friendFundingRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)


                friendFundingRecyclerView.adapter = friendFundingListadapter

                friendFundingRecyclerView.addItemDecoration(LinearItemDecoration(10))

                // 리사이클러뷰 임의데이터 설정
                friendFundingListadapter.data = listOf(
                    FriendFunding(
                        profileImg = R.drawable.profile1,
                        profileName = "포키포키",
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

                scrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
                    shadow.isActivated = v?.canScrollVertically(-1) ?: false
                }

            }




} // end class
