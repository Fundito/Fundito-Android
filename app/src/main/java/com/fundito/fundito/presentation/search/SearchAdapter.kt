package com.fundito.fundito.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.ItemSearchBinding

/**
 * Created by mj on 29, December, 2019
 */
class SearchAdapter(private val onItemClick : () -> Unit) : ListAdapter<String, SearchAdapter.SearchHolder>(DIFF) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)

        return SearchHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchHolder, position: Int) = holder.bind(getItem(position))


    inner class SearchHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

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

@BindingAdapter("app:recyclerview_Search_items")
fun RecyclerView.setItems(items: List<String>?) {
     if(items == null) return
    (adapter as? SearchAdapter)?.run {
        this.submitItems(items)
    }
}