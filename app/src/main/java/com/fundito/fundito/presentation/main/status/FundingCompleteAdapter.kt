package com.fundito.fundito.presentation.main.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.CompleteFundingResponse
import com.fundito.fundito.databinding.ItemFundingCompleteBinding

/**
 * Created by mj on 28, December, 2019
 */
class FundingCompleteAdapter(private val onItemClick : (CompleteFundingResponse) -> Unit) : ListAdapter<CompleteFundingResponse, FundingCompleteAdapter.FundingCompleteHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CompleteFundingResponse>() {
            override fun areItemsTheSame(oldItem: CompleteFundingResponse, newItem: CompleteFundingResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CompleteFundingResponse, newItem: CompleteFundingResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<CompleteFundingResponse>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingCompleteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFundingCompleteBinding.inflate(inflater, parent, false)

        return FundingCompleteHolder(binding)
    }


    override fun onBindViewHolder(holder: FundingCompleteHolder, position: Int) = holder.bind(getItem(position))


    inner class FundingCompleteHolder(private val binding: ItemFundingCompleteBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: CompleteFundingResponse) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.name.text = item.storeName
            binding.funding.text = "${item.fundingMoney.toMoney()} 원 투자"

            binding.completeDay.text = DateParsingUtil.parseToYMD(DateParsingUtil.parseString(item.dueDate))
            binding.getMoney.text = buildSpannedString {
                bold{append(item.refundMoney.toMoney())}
                append(" 원 회수")
            }
        }
    }
}

@BindingAdapter("app:recyclerview_FundingComplete_items")
fun RecyclerView.setItemsComplete(items: List<CompleteFundingResponse>?) {
     if(items == null) return
    (adapter as? FundingCompleteAdapter)?.run {
        this.submitItems(items)
    }
}