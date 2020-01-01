package com.fundito.fundito.presentation.main.home

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.application.MainApplication
import com.fundito.fundito.common.loadUrlAsync
import com.fundito.fundito.common.util.DateParsingUtil
import com.fundito.fundito.common.util.PermissionUtil
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.CustomTypefaceSpan
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentHomeBinding
import com.fundito.fundito.presentation.main.MainActivity
import com.fundito.fundito.presentation.noti.NotiActivity
import com.fundito.fundito.presentation.search.SearchActivity
import com.fundito.fundito.presentation.store.StoreDetailActivity
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class HomeFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mBinding : FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel : HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        = FragmentHomeBinding.inflate(inflater,container,false).run { mBinding = this ; root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        requestPermission()
        initView()
        adjustSystemUI()
        observeViewModel()
        observeWifiSSID()
    }

    private fun requestPermission() {
        PermissionUtil.requestPermissions(requireActivity(),object : PermissionUtil.PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionShouldBeGranted(deniedPermissions: List<String>) {
            }

            override fun onAnyPermissionsPermanentlyDeined(deniedPermissions: List<String>, permanentDeniedPermissions: List<String>) {
            }
        },listOf(android.Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun initView() {
        mBinding.notiButton setOnDebounceClickListener {
            startActivity(NotiActivity::class)
        }
        mBinding.searchContainer setOnDebounceClickListener {
            startActivity(
                Intent(requireContext(),SearchActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(requireActivity(), Pair.create( mBinding.searchContainer,"searchbar")  ).toBundle()
            )
        }
    }

    private fun adjustSystemUI() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if(MainActivity.menu.value == MainActivity.MainMenu.HOME) {
                requireActivity().window.statusBarColor = Color.parseColor("#f6f5f5")
            }
        }
    }

    private fun observeViewModel() {
        mViewModel.apply {
            userData.observe(viewLifecycleOwner) {user->

                val lightTypeface = ResourcesCompat.getFont(requireContext(), R.font.spoqa_han_sans_light)

                mBinding.info.text = buildSpannedString {
                    append(user.name)

                    lightTypeface?.let {
                        inSpans(CustomTypefaceSpan("",lightTypeface)) {
                            append("님,\n식사는 잘 하셨나요?")
                        }
                    } ?: run {
                        append("님,\n식사는 잘 하셨나요?")
                    }
                }
            }

            connectedStoreData.observe(viewLifecycleOwner) {data->
                mBinding.storeCard setOnDebounceClickListener {
                    startActivity(StoreDetailActivity.newIntent(requireContext(),data.storeIdx))
                }
                mBinding.thumbnail.loadUrlAsync(data.thumbnail)
                mBinding.shopName.text = data.storeName
                mBinding.remain.text = "${data.remainingDays}일"
                mBinding.ssidName.text = data.wifiSSID
                mBinding.progressView.progress = data.progressPercent

            }
            connectedStoreTimeLineItem.observe(viewLifecycleOwner) {funding->
                mBinding.fundingAmmount.text = "${funding.fundingMoney}원 투자"
                mBinding.nickName.text = funding.nickname
                mBinding.timeDiff.text = DateParsingUtil.calculateDiffWithCurrent(funding.fundingTime,false)

            }
        }
    }

    private fun observeWifiSSID() {
        MainApplication.wifiSSID.observe(viewLifecycleOwner) {ssid->
            if(ssid.isBlank()) {
                mBinding.storeCard setOnDebounceClickListener {}
            }
            Timber.e(ssid)
//            mViewModel.onWifiStateChanged(ssid)
            mViewModel.onWifiStateChanged("234")
        }
    }

}
