package com.fundito.fundito.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.fundito.fundito.R
import com.fundito.fundito.common.showAlert
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.CardCreateRequest
import kotlinx.android.synthetic.main.activity_card_register.*


/**
 * Created by mj on 26, December, 2019
 */
class CardRegisterActivity : AppCompatActivity() {

    companion object {

        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_NICKNAME = "ARG_NICKNAME"

        fun newIntent(context: Context, name: String, nickname: String) = Intent(context, CardRegisterActivity::class.java).apply {
            putExtra(ARG_NAME, name)
            putExtra(ARG_NICKNAME, nickname)
        }
    }

    private val userName: String
        get() = intent?.getStringExtra(ARG_NAME) ?: ""
    private val nickName: String
        get() = intent?.getStringExtra(ARG_NICKNAME) ?: ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        scrollView.setOnScrollChangeListener { v, _, _, _, _ ->
            shadow.isActivated = v.canScrollVertically(-1)
        }

        usernameTextView.text = userName

        makeController()
        /**
         * Formatting a credit card number: ####-####-####-####
         */
        cardNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {

                //숫자 4개 입력할때마다 - 생김
                if (start == 3 && start + added == 4 && p0?.contains('-') == false) {
                    cardNumberEditText.setText("$p0-")
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
                } else if (start == 8 && start + added == 9 && p0?.contains('-') == true) {
                    cardNumberEditText.setText("$p0-")
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
                } else if (start == 13 && start + added == 14 && p0?.contains('-') == true) {
                    cardNumberEditText.setText("$p0-")
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
                }
                // /다음 숫자 지우면 /까지 사라짐
                else if (start == 4 && start - removed == 3 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
                } else if (start == 9 && start - removed == 8 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
                } else if (start == 14 && start - removed == 13 && p0?.contains('/') == true) {
                    cardNumberEditText.setText(p0.toString().replace("/", ""))
                    cardNumberEditText.setSelection(cardNumberEditText.text?.length ?: 0)
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
                    expirydateEditText.setText("$p0/")
                    expirydateEditText.setSelection(expirydateEditText.text?.length ?: 0)
                } else if (start == 3 && start - removed == 2 && p0?.contains('/') == true) {
                    expirydateEditText.setText(p0.toString().replace("/", ""))
                    expirydateEditText.setSelection(expirydateEditText.text?.length ?: 0)
                }
            }
        })
        backButton setOnDebounceClickListener  {
            finish()
        }

        //전체선택
        checkbox_all.setOnCheckedChangeListener { _, isChecked ->
            checkbox2.isChecked = isChecked
            checkbox3.isChecked = isChecked
            checkbox4.isChecked = isChecked
            checkbox5.isChecked = isChecked
            constraintLayout1.isVisible = !isChecked
            constraintLayout2.isVisible = !isChecked
            constraintLayout3.isVisible = !isChecked
            constraintLayout4.isVisible = !isChecked
        }

    }

    private fun makeController() {

        registerButton setOnDebounceClickListener  {
            val cardnumber = cardNumberEditText.text.toString()
            val date = expirydateEditText.text.toString()
            val pw = passwordEditText.text.toString()
            val cardname = cardnameEditText.text.toString()
            val checkboxAll = checkbox_all

            cardNumberLayout.error = null
            expirydateLayout.error = null
            passwordLayout.error = null
            cardnameLayout.error = null

            when {
                cardnumber.length != 19 -> {
                    cardNumberLayout.error = "카드번호 미입력"
                    return@setOnDebounceClickListener
                }
                date.isBlank() -> {
                    expirydateLayout.error = "만료기한 미입력"
                    return@setOnDebounceClickListener
                }
                pw.length != 2-> {
                    passwordLayout.error = "비밀번호 미입력"
                    return@setOnDebounceClickListener
                }
                cardname.isBlank()-> {
                    cardnameLayout.error = "카드이름 미입력"
                    return@setOnDebounceClickListener
                }
                !checkboxAll.isChecked-> {
                    showAlert("약관 동의가 필요합니다")
                    return@setOnDebounceClickListener
                }
                else-> {
                    startActivity(LoginPasswordActivity.newIntent(this@CardRegisterActivity,userName,nickName,
                        CardCreateRequest(
                            "국민",
                            cardname,
                            cardnumber,
                            date,
                            pw
                        )
                    ))
                }

            }



        }

    }


}







