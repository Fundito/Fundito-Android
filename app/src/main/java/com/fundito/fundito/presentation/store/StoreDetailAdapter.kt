package com.fundito.fundito.presentation.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.databinding.ItemStoreDetailBinding

/**
 * Created by mj on 30, December, 2019
 */
typealias StoreDetailItem = Pair<String,String>
class StoreDetailAdapter : ListAdapter<StoreDetailItem,StoreDetailAdapter.StoreDetailHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<StoreDetailItem>() {
            override fun areItemsTheSame(oldItem: StoreDetailItem, newItem: StoreDetailItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoreDetailItem, newItem: StoreDetailItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<StoreDetailItem>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreDetailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreDetailBinding.inflate(inflater, parent, false)

        return StoreDetailHolder(binding)
    }


    override fun onBindViewHolder(holder: StoreDetailHolder, position: Int) = holder.bind(getItem(position))


    inner class StoreDetailHolder(private val binding: ItemStoreDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoreDetailItem) {
            binding.setVariable(BR.leftText, item.first)
            binding.setVariable(BR.rightText, item.second)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_StoreDetail_items")
fun RecyclerView.setItems(items: List<StoreDetailItem>?) {
     if(items == null) return
    (adapter as? StoreDetailAdapter)?.run {
        this.submitItems(items)
    }
}