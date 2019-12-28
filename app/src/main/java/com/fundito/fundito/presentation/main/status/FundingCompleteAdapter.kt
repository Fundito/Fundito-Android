package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.databinding.ItemFundingCompleteBinding

/**
 * Created by mj on 28, December, 2019
 */
class FundingCompleteAdapter : ListAdapter<String, FundingCompleteAdapter.FundingCompleteHolder>(DIFF) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingCompleteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFundingCompleteBinding.inflate(inflater, parent, false)

        return FundingCompleteHolder(binding)
    }


    override fun onBindViewHolder(holder: FundingCompleteHolder, position: Int) = holder.bind(getItem(position))


    inner class FundingCompleteHolder(private val binding: ItemFundingCompleteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_FundingComplete_items")
fun RecyclerView.setItemsComplete(items: List<String>?) {
     if(items == null) return
    (adapter as? FundingCompleteAdapter)?.run {
        this.submitItems(items)
    }
}