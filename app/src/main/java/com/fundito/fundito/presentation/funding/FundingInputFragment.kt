package com.fundito.fundito.presentation.funding


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.common.setVisibilityBinding
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.android.synthetic.main.fragment_funding_input.*
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by mj on 26, December, 2019
 */
class FundingInputFragment : Fragment() {
    var requiredChargingCost:String =""
    var requiredChargingNum: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_input,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            kotlin.runCatching {
                NetworkClient.cardService.getCard()
            }
                .onSuccess {
                    val cardnumber : String = it.cardNickname
                    fundingCardNumber.text = cardnumber
                }
                .onFailure {
                    Timber.e("fail")
                    Timber.e(it.message.toString())
                }
        }

        lifecycleScope.launch{
            kotlin.runCatching {
                NetworkClient.userService.getFunditoMoney()
            }
                .onSuccess {
                    it.getOrNull(0)?.let{
                        funditoMoney.text = "펀디토머니:${it}원/부족금액은 자동충전됨"
                        requiredChargingCost = it.toString()
                        requiredChargingNum = Integer.parseInt(requiredChargingCost)
                    }
                }
                .onFailure {
                    Timber.e("fail")
                    Timber.e(it.message.toString())
                }
        }

        if(fundinginput_txt.text ==""){
            requiredCharging.setVisibilityBinding(false)
            fundingCardNumber.setVisibilityBinding(false)
            funditoMoney.setVisibilityBinding(true)
        }
        else{
            requiredCharging.setVisibilityBinding(true)
            fundingCardNumber.setVisibilityBinding(true)
            funditoMoney.setVisibilityBinding(false)
        }
        dialView.onInvestmentValueChangedListener = {number->
            fundinginput_txt.setTextColor(Color.BLACK)
            fundinginput_txt.text = "${number.toLong().toMoney()}"
            if(requiredChargingNum - number >0){
                requiredCharging.text = "충전:0원"
            }
            else{
                requiredCharging.text = "충전:${number - requiredChargingNum}원"
            }
        }


    }

    
}