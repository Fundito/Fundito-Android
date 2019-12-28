package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.databinding.ItemRecentFundingBinding

/**
 * Created by mj on 28, December, 2019
 */
class RecentFundingAdapter : ListAdapter<String, RecentFundingAdapter.RecentFundingHolder>(DIFF) {
    
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<String>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentFundingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentFundingBinding.inflate(inflater, parent, false)

        return RecentFundingHolder(binding)
    }


    override fun onBindViewHolder(holder: RecentFundingHolder, position: Int) = holder.bind(getItem(position))


    inner class RecentFundingHolder(private val binding: ItemRecentFundingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
//            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_RecentFunding_items")
fun RecyclerView.setItems(items: List<String>?) {
     if(items == null) return
    (adapter as? RecentFundingAdapter)?.run {
        this.submitItems(items)
    }
}