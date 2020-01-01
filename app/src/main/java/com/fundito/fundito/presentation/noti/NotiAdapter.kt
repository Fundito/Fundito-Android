package com.fundito.fundito.presentation.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.application.MainApplication.Companion.GlobalApp
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.enumerator.FundStatus
import com.fundito.fundito.data.service.NotificationResponse
import com.fundito.fundito.databinding.ItemNotiBinding
import java.util.*

/**
 * Created by mj on 29, December, 2019
 */
class NotiAdapter(private val onItemClick: (NotificationResponse) -> Unit) : androidx.recyclerview.widget.ListAdapter<NotificationResponse,NotiAdapter.NotiHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<NotificationResponse>() {
            override fun areItemsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
                return oldItem.notificationIdx == newItem.notificationIdx
            }

            override fun areContentsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<NotificationResponse>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotiBinding.inflate(inflater, parent, false)

        return NotiHolder(binding)
    }


    override fun onBindViewHolder(holder: NotiHolder, position: Int) = holder.bind(getItem(position))


    inner class NotiHolder(private val binding: ItemNotiBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root setOnDebounceClickListener {
                onItemClick(currentList[layoutPosition])
            }
        }

        fun bind(item: NotificationResponse) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            val cal = DateParsingUtil.parseString(item.date)
            binding.date.text = "%02d월 %02d일".format(cal[Calendar.MONTH] + 1,cal[Calendar.DAY_OF_MONTH])
            binding.name.text = (item.storeInfo?.name ?: "") + "(이/가)"
            binding.progress.text = when(item.storeInfo?.fundStatus ?: null) {
                FundStatus.SUCCESS-> buildSpannedString {
                    append("목표매출 ")
                    color(GlobalApp.resources.getColor(R.color.dark_navy)){append("도달!")}
                }
                else-> buildSpannedString {
                    append("목표매출 ")
                    color(GlobalApp.resources.getColor(R.color.dark_navy)){append("실패")}
                }
            }
        }
    }
}

@BindingAdapter("app:recyclerview_Noti_items")
fun RecyclerView.setItems(items: List<NotificationResponse>?) {
     if(items == null) return
    (adapter as? NotiAdapter)?.run {
        this.submitItems(items)
    }
}