package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.android.synthetic.main.fragment_funding_input.*
import kotlinx.android.synthetic.main.fragment_funding_progress.*
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by mj on 26, December, 2019
 */
class FundingProgressFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var percent : Double
        fundingPriceProgress.text = fundinginput_txt.text
        lifecycleScope.launch {
            kotlin.runCatching {
                var a = NetworkClient.fundingService.getMaxInterestRate()
                refundPercent.text = "${a.refundPercent}%이율"
                percent = (a.refundPercent-100)*0.01

                linewon.text = "${(Integer.parseInt(fundingPriceProgress.text.toString())*percent)}원"

                fundingTotal.text="${Integer.parseInt(fundingPriceProgress.text.toString())*percent + Integer.parseInt(fundingPriceProgress.text.toString())}원"
            }
                .onSuccess {
                    Timber.e("success")
                }
                .onFailure {
                    Timber.e("Fail")
                    Timber.e(it.message.toString())
                }
        }
        lifecycleScope.launch{
            kotlin.runCatching {
                NetworkClient.userService.getFunditoMoney()
            }
                .onSuccess {
                    it.getOrNull(0)?.let{
                        funditoMoneyShowing.text = "펀디토머니:${it}원"
                    }
                }
                .onFailure {
                    Timber.e("fail")
                    Timber.e(it.message.toString())
                }
        }
    }
}