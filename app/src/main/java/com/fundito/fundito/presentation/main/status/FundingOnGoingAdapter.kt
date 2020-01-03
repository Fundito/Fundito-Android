package com.fundito.fundito.presentation.main.status

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.common.util.toPixel
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.CurrentFundingResponse
import com.fundito.fundito.databinding.ItemFundingOngoingBinding

/**
 * Created by mj on 28, December, 2019
 */
class FundingOnGoingAdapter(private val onItemClick : (CurrentFundingResponse) -> Unit,private val cardShadow: Boolean = false) : ListAdapter<CurrentFundingResponse, FundingOnGoingAdapter.FundingOnGoingHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CurrentFundingResponse>() {
            override fun areItemsTheSame(oldItem: CurrentFundingResponse, newItem: CurrentFundingResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CurrentFundingResponse, newItem: CurrentFundingResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<CurrentFundingResponse>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingOnGoingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFundingOngoingBinding.inflate(inflater, parent, false)

        if(cardShadow) {
            (binding.root as CardView).setCardBackgroundColor(ColorStateList.valueOf(Color.WHITE))
            (binding.root as CardView).elevation = 16.toPixel()
        }

        return FundingOnGoingHolder(binding)
    }


    override fun onBindViewHolder(holder: FundingOnGoingHolder, position: Int) = holder.bind(getItem(position))


    inner class FundingOnGoingHolder(private val binding: ItemFundingOngoingBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: CurrentFundingResponse) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
            binding.name.text = item.storeName

            if(item.remainingDays < 0 && item.progressPercent < 0) {
                binding.root.alpha = 0.5f
                binding.remainDay.text = "마감"
                binding.progress.text = "목표 실패"
                binding.progressView.progress = 100
            }else {
                binding.root.alpha = 1.0f
                binding.remainDay.text = "${item.remainingDays}일 남음"
                binding.progress.text = "${item.progressPercent}% 달성중"
                binding.progressView.progress = item.progressPercent
            }




        }
    }
}

@BindingAdapter("app:recyclerview_FundingOnGoing_items")
fun RecyclerView.setItemsFundingOnGoing(items: List<CurrentFundingResponse>?) {
     if(items == null) return
    (adapter as? FundingOnGoingAdapter)?.run {
        this.submitItems(items)
    }
}