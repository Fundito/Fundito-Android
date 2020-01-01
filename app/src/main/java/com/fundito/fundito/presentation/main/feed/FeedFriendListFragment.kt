package com.fundito.fundito.presentation.main.feed

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.data.model.FriendFunding
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_feed_friend_list.*
import kotlinx.coroutines.launch

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


        friendFundingListadapter = FriendFundingListAdapter(this.context!!)

        friendFundingRecyclerView = view.findViewById(R.id.friendFundingRecyclerView)

        friendFundingRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)


        friendFundingRecyclerView.adapter = friendFundingListadapter

        friendFundingRecyclerView.addItemDecoration(LinearItemDecoration(10))


        friendFundingListadapter.notifyDataSetChanged()

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            shadow.isActivated = v?.canScrollVertically(-1) ?: false
        }

        adjustSystemUI()
    }

    private fun loadData() {
        lifecycleScope.launch {

        }
    }

    private fun adjustSystemUI() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if(MainActivity.menu.value == MainActivity.MainMenu.FEED) {
                requireActivity().window.statusBarColor = Color.parseColor("#f6f5f5")
            }
        }
    }


} // end class
