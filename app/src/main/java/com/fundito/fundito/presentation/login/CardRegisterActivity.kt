package com.fundito.fundito.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_card_register.*
import kotlinx.android.synthetic.main.activity_login_nickname.*
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by mj on 26, December, 2019
 */
class CardRegisterActivity : AppCompatActivity() {
    private val userName = String
    private val TAG = "CardRegisterActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        val intent = getIntent()
        usernameTextView.text =intent.getStringExtra("${username}")

        makeController()
        initview()
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
        backButton.setOnClickListener(){
            val intent = Intent(this@CardRegisterActivity, LoginNicknameActivity::class.java)
            startActivity(intent)
        }

        //전체선택
        checkbox_all.setOnClickListener {
            //            checkbox_all.toggle()
//            checkbox2.toggle()
//            checkbox3.toggle()
//            checkbox5.toggle()
            checkbox_all.isSelected = true
            checkbox2.isSelected = true
            checkbox3.isSelected = true
            checkbox4.isSelected = true
            checkbox5.isSelected = true
            if(checkbox_all.isChecked == false){
                checkbox_all.isSelected = false
                checkbox2.isSelected = false
                checkbox3.isSelected = false
                checkbox4.isSelected = false
                checkbox5.isSelected = false
            }
        }

//        (checkbox2.isSelected = true || checkbox3.isSelected = true  || checkbox4.isSelected =true  || checkbox5.isSelected =true  ){
//            checkbox_all.isSelected = true
//        }

    }

    fun makeController() {

        registerButton.setOnClickListener {
            val cardnumber = cardNumberEditText.text.toString()
            val date = expirydateEditText.text.toString()
            val pw = passwordEditText.text.toString()
            val cardname = cardnameEditText.text.toString()
            val checkboxAll = checkbox_all

            // 빈 칸이 있으면 안되므로 빈 칸 체크
            if (cardnumber.isEmpty() || date.isEmpty() || pw.isEmpty() || cardname.isEmpty()) {
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (checkboxAll.isSelected == false) {

                Toast.makeText(this, "약관동의를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@CardRegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun initview(){
        lifecycleScope.launch {
            kotlin.runCatching {

                var a = NetworkClient.cardService.createCard(
                    "국민",
                    cardnameEditText.text.toString(),
                    cardNumberEditText.text.toString(),
                    expirydateEditText.text.toString(),
                    passwordEditText.text.toString())
                Timber.e("Faill")

                intent.getStringExtra("name")

            }
                .onSuccess {
                    Timber.e("success")
                }
                .onFailure {
                    Timber.e("Fail")
                    Timber.e(it.message.toString())
                }
        }
    }

}







