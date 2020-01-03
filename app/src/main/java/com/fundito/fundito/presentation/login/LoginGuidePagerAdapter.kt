package com.fundito.fundito.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.databinding.ItemLoginGuideBinding

/**
 * Created by mj on 03, January, 2020
 */
class LoginGuidePagerAdapter : ListAdapter<Triple<String, String, @DrawableRes Int>, LoginGuidePagerAdapter.LoginGuidePagerHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Triple<String,String,@androidx.annotation.DrawableRes Int>>() {
            override fun areItemsTheSame(oldItem: Triple<String,String, Int>, newItem: Triple<String,String, Int>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Triple<String,String, Int>, newItem: Triple<String,String, Int>): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitItems(items : List<Triple<String,String, Int>>) {
        submitList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginGuidePagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoginGuideBinding.inflate(inflater, parent, false)

        return LoginGuidePagerHolder(binding)
    }


    override fun onBindViewHolder(holder: LoginGuidePagerHolder, position: Int) = holder.bind(getItem(position))


    inner class LoginGuidePagerHolder(private val binding: ItemLoginGuideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Triple<String,String, Int>) {
            binding.setVariable(BR.info1, item.first)
            binding.setVariable(BR.info2, item.second)
            binding.setVariable(BR.image, item.third)
            binding.executePendingBindings()
        }
    }
}
