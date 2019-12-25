package com.fundito.fundito.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.showProgress

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProgress()

    }
}
