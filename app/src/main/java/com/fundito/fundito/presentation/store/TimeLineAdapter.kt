package com.fundito.fundito.presentation.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.databinding.ItemTimelineBinding

/**
 * Created by mj on 26, December, 2019
 */
class TimeLineAdapter : ListAdapter<String, TimeLineAdapter.TimeLineHolder>(DIFF) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(inflater, parent, false)

        return TimeLineHolder(binding)
    }


    override fun onBindViewHolder(holder: TimeLineHolder, position: Int) = holder.bind(getItem(position))


    inner class TimeLineHolder(private val binding: ItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_TimeLine_items")
fun RecyclerView.setItems(items: List<String>?) {
     if(items == null) return
    (adapter as? TimeLineAdapter)?.run {
        this.submitItems(items)
    }
}