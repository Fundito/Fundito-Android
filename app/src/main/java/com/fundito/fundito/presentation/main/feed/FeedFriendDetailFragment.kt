package com.fundito.fundito.presentation.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fundito.fundito.R

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_feed_friend_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}