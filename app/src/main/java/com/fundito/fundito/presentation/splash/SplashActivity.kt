package com.fundito.fundito.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.ItemSampleBinding
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by mj on 22, December, 2019
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//        },2000L)

//        ObjectAnimator.ofFloat(button,"translationX",500f).apply {
//            duration = 1000L
//            repeatMode = ValueAnimator.REVERSE
//            repeatCount = ValueAnimator.INFINITE
//            start()
//        }

        button setOnDebounceClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        investment.onInvestmentValueChangedListener = {
            button.text = it.toString()
        }

        recyclerView.apply {
            adapter = SampleAdapter {

            }.apply {
                submitItems(listOf("1","2","3","4"))
            }
        }
    }
}

class SampleAdapter(private val itemClick : (String) -> Unit) : ListAdapter<String, SampleAdapter.SampleHolder>(DIFF) {


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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSampleBinding.inflate(inflater, parent, false)

        return SampleHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.bind(currentList[position])
    }


    inner class SampleHolder(private val binding: ItemSampleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.button.setOnDebounceClickListener {
                itemClick(currentList[layoutPosition])
            }
        }

        fun bind(item : String) {
            binding.button.text = item
        }
    }
}
