package com.fundito.fundito.presentation.main.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fundito.fundito.R
import com.fundito.fundito.data.model.FriendFunding

class FriendFundingListAdapter(private val context: Context) : RecyclerView.Adapter<FriendFundingViewHolder>() {
    var data: List<FriendFunding> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendFundingViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_friend_funding_list, parent, false)
        return FriendFundingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // 만들어진 ViewHolder에 데이터를 연결한다.
    override fun onBindViewHolder(holder: FriendFundingViewHolder, position: Int) {

        Glide.with(context)
            .load(data[position].profileImg)
            .into(holder.profileImage)

        holder.onBind(data[position])


    }

}