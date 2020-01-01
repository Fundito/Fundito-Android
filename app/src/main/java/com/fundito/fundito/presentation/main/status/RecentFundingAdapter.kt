package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.application.MainApplication.Companion.GlobalApp
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.databinding.ItemRecentFundingBinding
import java.util.*

/**
 * Created by mj on 28, December, 2019
 */
typealias RecentFundingItem = Pair<Store,Funding>
class RecentFundingAdapter : ListAdapter<RecentFundingItem, RecentFundingAdapter.RecentFundingHolder>(DIFF) {
    
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<RecentFundingItem>() {
            override fun areItemsTheSame(oldItem: RecentFundingItem, newItem: RecentFundingItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecentFundingItem, newItem: RecentFundingItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<RecentFundingItem>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentFundingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentFundingBinding.inflate(inflater, parent, false)

        return RecentFundingHolder(binding)
    }


    override fun onBindViewHolder(holder: RecentFundingHolder, position: Int) = holder.bind(getItem(position))


    inner class RecentFundingHolder(private val binding: ItemRecentFundingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentFundingItem) {
            val cal = DateParsingUtil.parseString(item.second.fundingTime)
            binding.date.text = "${cal[Calendar.MONTH] + 1}월 ${cal[Calendar.DAY_OF_MONTH]}일"
            binding.name.text = item.first.name
            binding.money.text = item.second.fundingMoney.toMoney() + "원"
            binding.refundMoney.text = buildSpannedString {
                append("회수금액 ")
                color(GlobalApp.getColor(R.color.blueberry_two)){ append("${item.second.rewardMoney.toMoney()}원 (${item.second.rewardPercent}%)") }
            }
            binding.time.text = "${cal[Calendar.HOUR_OF_DAY]}:${cal[Calendar.MINUTE]}"

        }
    }
}

@BindingAdapter("app:recyclerview_RecentFunding_items")
fun RecyclerView.setItems(items: List<RecentFundingItem>?) {
     if(items == null) return
    (adapter as? RecentFundingAdapter)?.run {
        this.submitItems(items)
    }
}