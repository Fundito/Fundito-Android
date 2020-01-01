package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.CurrentFundingResponse
import com.fundito.fundito.databinding.ItemFundingOngoingBinding

/**
 * Created by mj on 28, December, 2019
 */
class FundingOnGoingAdapter(private val onItemClick : (CurrentFundingResponse) -> Unit) : ListAdapter<CurrentFundingResponse, FundingOnGoingAdapter.FundingOnGoingHolder>(DIFF) {

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
            binding.remainDay.text = "${item.remainingDays}일 남음"
            binding.progress.text = "${item.progressPercent}% 달성중"
            binding.progressView.progress = item.progressPercent
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