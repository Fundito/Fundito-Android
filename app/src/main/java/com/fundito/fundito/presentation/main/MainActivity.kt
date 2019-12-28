package com.fundito.fundito.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.databinding.ActivityMainBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.main.feed.FeedFriendListFragment
import com.fundito.fundito.presentation.main.home.HomeFragment
import com.fundito.fundito.presentation.main.more.MoreFragment
import com.fundito.fundito.presentation.main.status.StatusFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    companion object {
        private val _menu : MutableLiveData<MainMenu> = MutableLiveData(MainMenu.HOME)
        val menu : LiveData<MainMenu> = _menu
    }

    enum class MainMenu(@IdRes val menuId: Int, val index: Int) {
        HOME(R.id.main_menu_1,0),
        FEED(R.id.main_menu_2,1),
        STATUS(R.id.main_menu_3,2),
        MORE(R.id.main_menu_4,3)

        ;

        companion object {
            fun parseIndex(index: Int) = values().firstOrNull { it.index == index } ?: HOME
            fun parseMenuId(@IdRes menuId: Int) = values().firstOrNull { it.menuId == menuId } ?: HOME
        }

    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var mBinding : ActivityMainBinding

    private lateinit var mViewModel : MainViewModel

    private lateinit var fragment1 : HomeFragment
    private lateinit var fragment2 : FeedFriendListFragment
    private lateinit var fragment3 : StatusFragment
    private lateinit var fragment4 : MoreFragment
    private lateinit var curFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        initFragments(savedInstanceState == null)


        initView()
        observeViewModel()
    }

    private fun initView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            _menu.value = MainMenu.parseMenuId(it.itemId)
            true
        }
    }

    private fun initFragments(isFirstCreation : Boolean) {

        fragment1 = supportFragmentManager.findFragmentByTag("0") as? HomeFragment ?: HomeFragment.newInstance()
        fragment2 = supportFragmentManager.findFragmentByTag("1") as? FeedFriendListFragment ?:FeedFriendListFragment.newInstance()
        fragment3 = supportFragmentManager.findFragmentByTag("2") as? StatusFragment ?: StatusFragment.newInstance()
        fragment4 = supportFragmentManager.findFragmentByTag("3") as? MoreFragment ?: MoreFragment.newInstance()
        curFragment = when(menu.value) {
            MainMenu.HOME->fragment1
            MainMenu.FEED->fragment2
            MainMenu.STATUS->fragment3
            MainMenu.MORE->fragment4
            else -> throw IllegalStateException()
        }

        if(isFirstCreation) {
            val fm = supportFragmentManager
            fm.beginTransaction().add(R.id.fragmentContainer, fragment4, "0").hide(fragment4).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment3, "1").hide(fragment3).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment2, "2").hide(fragment2).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment1, "3").commit()
        }
    }


    private fun changeFragment(menu : MainMenu) {

        val willShow = when (menu) {
            MainMenu.HOME -> fragment1
            MainMenu.FEED -> fragment2
            MainMenu.STATUS -> fragment3
            MainMenu.MORE -> fragment4
        }
        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
    }

    private fun observeViewModel() {
        menu.observe(this@MainActivity) { menu->
            changeFragment(menu)

            if(mBinding.bottomNavigation.selectedItemId != menu.menuId)
                mBinding.bottomNavigation.selectedItemId = menu.menuId
        }

        mViewModel.apply {

        }
    }
}
