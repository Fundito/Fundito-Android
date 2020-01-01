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
import com.fundito.fundito.R
import com.fundito.fundito.common.showAlert
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showLoading
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_feed_friend_list.*
import kotlinx.coroutines.launch

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendListFragment : Fragment() {

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


        val adapter = FriendFundingListAdapter {
            startActivity(FeedFriendDetailActivity.newIntent(requireContext(),it.userIdx))
        }

        friendFundingRecyclerView.layoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)

        friendFundingRecyclerView.adapter = adapter

        friendFundingRecyclerView.addItemDecoration(LinearItemDecoration(10))

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            shadow.isActivated = v?.canScrollVertically(-1) ?: false
        }

        adjustSystemUI()
        initView()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            showLoading()
            kotlin.runCatching {
                NetworkClient.friendService.monthlyDitoList()
            }.onSuccess {

                (friendFundingRecyclerView.adapter as? FriendFundingListAdapter)?.submitItems(it)

                it.getOrNull(0)?.let {res->
                    card1.setOnDebounceClickListener {
                        startActivity(FeedFriendDetailActivity.newIntent(requireContext(),res.userIdx))
                    }
                    profileLeftTextView.text = res.nickname
                    storenumberLeftTextView.text = "${res.fund.size}개 지점"
                }
                it.getOrNull(1)?.let {res->
                    card2.setOnDebounceClickListener {
                        startActivity(FeedFriendDetailActivity.newIntent(requireContext(),res.userIdx))
                    }
                    profileCenterTextView.text = res.nickname
                    storenumberCenterTextView.text = "${res.fund.size}개 지점"
                }
                it.getOrNull(2)?.let {res->
                    card3.setOnDebounceClickListener {
                        startActivity(FeedFriendDetailActivity.newIntent(requireContext(),res.userIdx))
                    }
                    profileRightTextView.text = res.nickname
                    storenumberRightTextView.text = "${res.fund.size}개 지점"
                }
            }.onFailure {
                showAlert("친구 목록을 불러오지 못했습니다")
            }
            hideLoading()
        }
    }

    private fun adjustSystemUI() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if (MainActivity.menu.value == MainActivity.MainMenu.FEED) {
                requireActivity().window.statusBarColor = Color.parseColor("#f6f5f5")
            }
        }
    }

    private fun initView() {

    }
} // end class
