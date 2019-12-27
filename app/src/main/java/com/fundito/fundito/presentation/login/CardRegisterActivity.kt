package com.fundito.fundito.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import kotlinx.android.synthetic.main.activity_card_register.*


/**
 * Created by mj on 26, December, 2019
 */
class CardRegisterActivity : AppCompatActivity() {
    private val TAG = "CardRegisterActivity"

    private val checkboxAll: CheckBox? = null
    private val check2: CheckBox? = null
    private val check3: CheckBox? = null
    private val check4: CheckBox? = null
    private val check5: CheckBox? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        /**
         * Formatting a credit card number: ####-####-####-####
         */
        cardNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {

                //숫자 4개 입력할때마다 - 생김
                if (start == 3 && start + added == 4 && p0?.contains('-') == false) {
                    cardNumberEditText.setText(p0.toString() + "-")
                    Log.d(TAG, "3")
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                } else if (start == 8 && start + added == 9 && p0?.contains('-') == true) {
                    Log.d(TAG, "8")
                    cardNumberEditText.setText(p0.toString() + "-")
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                } else if (start == 13 && start + added == 14 && p0?.contains('-') == true) {
                    cardNumberEditText.setText(p0.toString() + "-")
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                }
                // /다음 숫자 지우면 /까지 사라짐
                else if (start == 4 && start - removed == 3 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                } else if (start == 9 && start - removed == 8 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                } else if (start == 14 && start - removed == 13 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text.length)
                }
            }
        })


        /**
         * Formatting a credit card expire date : MM/YY
         */
        expirydateEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {

                if (start == 1 && start + added == 2 && p0?.contains('/') == false) {
                    expirydateEditText.setText(p0.toString() + "/")
                    Log.d(TAG, "ggggfffff")
                    expirydateEditText.setSelection(expirydateEditText.text.length)
                } else if (start == 3 && start - removed == 2 && p0?.contains('/') == true) {
                    expirydateEditText.setText(p0.toString().replace("/", ""))
                    expirydateEditText.setSelection(expirydateEditText.text.length)
                }
            }
        })

// checkAll 선택했을때 모두 선택되게


    }

}




/**
 * Formatting a credit card number: #### #### #### #######
 */

//    class CreditCardNumberFormattingTextWatcher : TextWatcher {
//        private var lock = false
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//        override fun afterTextChanged(s: Editable) {
//            if (lock || s.length > 16) {
//                return
//            }
//            lock = true
//            var i = 4
//            while (i < s.length) {
//                if (s.toString()[i] != ' ') {
//                    s.insert(i, " ")
//                }
//                i += 5
//            }
//            lock = false
//        }
//    }
/**
 * Formatting a credit card expire date : MM/YY
 */
//    class CreditCardExpiryDateFormattingTextWatcher : TextWatcher {
//
//        val expirydateEditText  = findViewById(R.id.expirydateEditText)
//        override fun afterTextChanged(p0: Editable?) {}
//
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {
//            if (start == 1 && start+added == 2 && p0?.contains('/') == false) {
//                expirydateEditText.setText(p0.toString() + "/")
//            } else if (start == 3 && start-removed == 2 && p0?.contains('/') == true) {
//                expirydateEditText.setText(p0.toString().replace("/", ""))
//            }
//        }
//    }




