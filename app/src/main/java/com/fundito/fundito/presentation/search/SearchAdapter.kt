package com.fundito.fundito.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.SearchResponseItem
import com.fundito.fundito.databinding.ItemSearchBinding

/**
 * Created by mj on 29, December, 2019
 */
class SearchAdapter(private val onItemClick : (SearchResponseItem) -> Unit) : ListAdapter<SearchResponseItem, SearchAdapter.SearchHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<SearchResponseItem>() {
            override fun areItemsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
                return oldItem.storeIdx == newItem.storeIdx
            }

            override fun areContentsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<SearchResponseItem>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)

        return SearchHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchHolder, position: Int) = holder.bind(getItem(position))


    inner class SearchHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: SearchResponseItem) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.progress.text = "${item.currentGaolPercent}% 달성중"
            val str = DateParsingUtil.calculateDiffWithCurrent(item.dueDate,true)
            binding.due.text = "남은기간 $str"
        }
    }
}

@BindingAdapter("app:recyclerview_Search_items")
fun RecyclerView.setItems(items: List<SearchResponseItem>?) {
     if(items == null) return
    (adapter as? SearchAdapter)?.run {
        this.submitItems(items)
    }
}