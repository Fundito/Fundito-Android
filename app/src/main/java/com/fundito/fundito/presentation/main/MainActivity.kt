package com.fundito.fundito.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.databinding.ActivityMainBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.main.feed.FeedFriendListFragment
import com.fundito.fundito.presentation.main.home.HomeFragment
import com.fundito.fundito.presentation.main.more.MoreFragment
import com.fundito.fundito.presentation.main.status.StatusBackgroundFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    enum class MainMenu(@IdRes val menuId : Int) {
        HOME(R.id.main_menu_1)
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var mBinding : ActivityMainBinding

    private lateinit var mViewModel : MainViewModel

    private val fragment1 = HomeFragment.newInstance()
    private val fragment2 = FeedFriendListFragment.newInstance()
    private val fragment3 = StatusBackgroundFragment.newInstance()
    private val fragment4 = MoreFragment.newInstance()
    private var curFragment: Fragment = fragment1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initFragments()
        initView()
        observeViewModel()
    }

    private fun initView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.main_menu_1-> {
                    changeFragment(0)
                }
                R.id.main_menu_2-> {
                    changeFragment(1)
                }
                R.id.main_menu_3-> {
                    changeFragment(2)
                }
                R.id.main_menu_4-> {
                    changeFragment(3)
                }
            }
            true
        }
    }

    private fun initFragments() {
        val fm = supportFragmentManager

        fm.beginTransaction().add(R.id.fragmentContainer, fragment4, null).hide(fragment4).commit()
        fm.beginTransaction().add(R.id.fragmentContainer, fragment3, null).hide(fragment3).commit()
        fm.beginTransaction().add(R.id.fragmentContainer, fragment2, null).hide(fragment2).commit()
        fm.beginTransaction().add(R.id.fragmentContainer, fragment1, null).commit()
    }


    private fun changeFragment(index: Int) {

        val willShow = when (index) {
            0 -> fragment1
            1 -> fragment2
            2 -> fragment3
            else -> fragment4
        }
        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
    }

    private fun observeViewModel() {
        mViewModel.apply {
            menuIndex.observe(this@MainActivity) {index->
                changeFragment(index)
            }
        }
    }
}
