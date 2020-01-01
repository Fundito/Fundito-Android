package com.fundito.fundito.presentation.main.more

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.presentation.main.MainActivity

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
    }
    private fun adjustSystemUI() {

        MainActivity.menu.observe(viewLifecycleOwner) {
            if(MainActivity.menu.value == MainActivity.MainMenu.MORE) {
                requireActivity().window.statusBarColor = Color.WHITE
            }
        }
    }

}