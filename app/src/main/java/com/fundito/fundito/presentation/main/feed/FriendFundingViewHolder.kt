package com.fundito.fundito.presentation.main.feed

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.fundito.fundito.R
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.model.FriendFunding
import com.fundito.fundito.generated.callback.OnClickListener
import com.fundito.fundito.presentation.main.MainActivity
import com.fundito.fundito.presentation.store.StoreDetailActivity

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