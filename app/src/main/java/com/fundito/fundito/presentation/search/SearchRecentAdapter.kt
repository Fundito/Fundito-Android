package com.fundito.fundito.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.database.SearchItem
import com.fundito.fundito.databinding.ItemSearchRecentBinding

/**
 * Created by mj on 29, December, 2019
 */
class SearchRecentAdapter(private val onItemClick: (SearchItem) -> Unit): androidx.recyclerview.widget.ListAdapter<SearchItem, SearchRecentAdapter.SearchRecentHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<SearchItem>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchRecentBinding.inflate(inflater, parent, false)

        return SearchRecentHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchRecentHolder, position: Int) = holder.bind(getItem(position))


    inner class SearchRecentHolder(private val binding: ItemSearchRecentBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: SearchItem) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_SearchRecent_items")
fun RecyclerView.setItems(items: List<SearchItem>?) {
     if(items == null) return
    (adapter as? SearchRecentAdapter)?.run {
        this.submitItems(items)
    }
}