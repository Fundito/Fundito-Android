package com.fundito.fundito.presentation.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import kotlinx.android.synthetic.main.activity_store_cheer.*

/**
 * Created by mj on 26, December, 2019
 */
class StoreCheerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_cheer)

        cancelButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    
}