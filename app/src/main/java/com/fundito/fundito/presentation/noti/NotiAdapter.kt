package com.fundito.fundito.presentation.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.databinding.ItemNotiBinding

/**
 * Created by mj on 29, December, 2019
 */
class NotiAdapter : androidx.recyclerview.widget.ListAdapter<String,NotiAdapter.NotiHolder>(DIFF) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotiBinding.inflate(inflater, parent, false)

        return NotiHolder(binding)
    }


    override fun onBindViewHolder(holder: NotiHolder, position: Int) = holder.bind(getItem(position))


    inner class NotiHolder(private val binding: ItemNotiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("app:recyclerview_Noti_items")
fun RecyclerView.setItems(items: List<String>?) {
     if(items == null) return
    (adapter as? NotiAdapter)?.run {
        this.submitItems(items)
    }
}