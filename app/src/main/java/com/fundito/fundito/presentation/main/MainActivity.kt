package com.fundito.fundito.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.showLoading
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
