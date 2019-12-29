package com.fundito.fundito.presentation.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.databinding.ItemTimelineBinding

/**
 * Created by mj on 26, December, 2019
 */
class TimeLineAdapter : ListAdapter<Funding, TimeLineAdapter.TimeLineHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Funding>() {
            override fun areItemsTheSame(oldItem: Funding, newItem: Funding): Boolean {
                return oldItem.fundingIdx == newItem.fundingIdx
            }

            override fun areContentsTheSame(oldItem: Funding, newItem: Funding): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<Funding>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)

        return TimeLineHolder(binding)
    }


    override fun onBindViewHolder(holder: TimeLineHolder, position: Int) = holder.bind(getItem(position))


    inner class TimeLineHolder(private val binding: ItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Funding) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.fundingMoney.text = "${item.fundingMoney.toMoney()}원 투자"

            //일, 시간,분

            binding.timeBefore.text = DateParsingUtil.calculateDiffWithCurrent(item.fundingTime)

        }
    }
}

@BindingAdapter("app:recyclerview_TimeLine_items")
fun RecyclerView.setItems(items: List<Funding>?) {
     if(items == null) return
    (adapter as? TimeLineAdapter)?.run {
        this.submitItems(items)
    }
}