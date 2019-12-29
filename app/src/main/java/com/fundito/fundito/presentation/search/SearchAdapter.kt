package com.fundito.fundito.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.databinding.ItemSearchBinding

/**
 * Created by mj on 29, December, 2019
 */
class SearchAdapter(private val onItemClick : (Store) -> Unit) : ListAdapter<Store, SearchAdapter.SearchHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem.storeIdx == newItem.storeIdx
            }

            override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<Store>) {
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

        fun bind(item: Store) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.progress.text = "${item.currentGoalPercent}% 달성중"
            binding.due.text = "남은기간 ${item.leftDay}일"
        }
    }
}

@BindingAdapter("app:recyclerview_Search_items")
fun RecyclerView.setItems(items: List<Store>?) {
     if(items == null) return
    (adapter as? SearchAdapter)?.run {
        this.submitItems(items)
    }
}