package com.fundito.fundito.presentation.main.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.BR
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.MonthlyDitoResponse
import com.fundito.fundito.databinding.ItemFriendFundingListBinding

class FriendFundingListAdapter(private val onItemClick : (MonthlyDitoResponse) -> Unit) : ListAdapter<MonthlyDitoResponse, FriendFundingListAdapter.FriendFundingListHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<MonthlyDitoResponse>() {
            override fun areItemsTheSame(oldItem: MonthlyDitoResponse, newItem: MonthlyDitoResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MonthlyDitoResponse, newItem: MonthlyDitoResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<MonthlyDitoResponse>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendFundingListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendFundingListBinding.inflate(inflater, parent, false)

        return FriendFundingListHolder(binding)
    }


    override fun onBindViewHolder(holder: FriendFundingListHolder, position: Int) = holder.bind(getItem(position))


    inner class FriendFundingListHolder(private val binding: ItemFriendFundingListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: MonthlyDitoResponse) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.profileNameTextView.text = item.name
            binding.fundingnumberTextView.text = "${item.fund.size}개 지점 투자 중"
//            binding.profileImageView.loadUrlAsync(item)
        }
    }
}

@BindingAdapter("app:recyclerview_FriendFundingList_items")
fun RecyclerView.setItems(items: List<MonthlyDitoResponse>?) {
     if(items == null) return
    (adapter as? FriendFundingListAdapter)?.run {
        this.submitItems(items)
    }
}