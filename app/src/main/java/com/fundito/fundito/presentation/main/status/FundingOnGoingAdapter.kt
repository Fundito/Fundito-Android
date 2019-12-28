package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.ItemFundingOngoingBinding

/**
 * Created by mj on 28, December, 2019
 */
class FundingOnGoingAdapter(private val onItemClick : () -> Unit) : ListAdapter<String, FundingOnGoingAdapter.FundingOnGoingHolder>(DIFF) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingOnGoingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFundingOngoingBinding.inflate(inflater, parent, false)

        return FundingOnGoingHolder(binding)
    }


    override fun onBindViewHolder(holder: FundingOnGoingHolder, position: Int) = holder.bind(getItem(position))


    inner class FundingOnGoingHolder(private val binding: ItemFundingOngoingBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick()
            }
        }

        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_FundingOnGoing_items")
fun RecyclerView.setItemsFundingOnGoing(items: List<String>?) {
     if(items == null) return
    (adapter as? FundingOnGoingAdapter)?.run {
        this.submitItems(items)
    }
}