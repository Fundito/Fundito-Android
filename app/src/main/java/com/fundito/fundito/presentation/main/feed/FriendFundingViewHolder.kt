package com.fundito.fundito.presentation.main.feed

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.data.model.FriendFunding

class FriendFundingViewHolder (view: View): RecyclerView.ViewHolder(view){
    private val numberStrTail = "개 지점 투자중"
    val profileImage : ImageView = view.findViewById(R.id.profileImageView)
    val profileName : TextView = view.findViewById(R.id.profileNameTextView)
    val fundingnumber : TextView = view.findViewById(R.id.fundingnumberTextView)


    fun onBind(friendFunding: FriendFunding){
        profileName.text = friendFunding.profileName
        fundingnumber.text = friendFunding.fundingNumber.toString() + numberStrTail
   }
}