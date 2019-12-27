package com.fundito.fundito.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.databinding.ActivityMainBinding
import com.fundito.fundito.di.module.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var mBinding : ActivityMainBinding

    private lateinit var mViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
    }

    private fun initView() {

    }
}
