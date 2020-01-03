package com.fundito.fundito.presentation.main.more

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.util.AuthUtil
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.presentation.main.MainActivity
import com.fundito.fundito.presentation.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_more.*

/**
 * Created by mj on 26, December, 2019
 */
class MoreFragment : Fragment() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_more,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adjustSystemUI()

        logoutButton setOnDebounceClickListener {
            AuthUtil.logout()
            startActivity(SplashActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
    private fun adjustSystemUI() {

        MainActivity.menu.observe(viewLifecycleOwner) {
            if(MainActivity.menu.value == MainActivity.MainMenu.MORE) {
                requireActivity().window.statusBarColor = Color.WHITE
                requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}