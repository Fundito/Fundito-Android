package com.fundito.fundito.presentation.login

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import kotlinx.android.synthetic.main.activity_login_nickname.*
import java.util.regex.Pattern

class LoginNicknameActivity : AppCompatActivity() {
    val nicknamePattern: String = "^[A-Za-z[0-9]]{2,8}$" // 영문, 숫자
    var chkFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_nickname)

       nicknameEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                button.visibility = View.VISIBLE
                if (nickNameCheckPattern(nicknameEditText.text.toString())) {
                    confirmTextView.setText("검사 완료")
                } else {
                    confirmTextView.setText("2-8글자 사이로 영어 숫자로만 입력해주세요")
                }
                // 입력되는 텍스트에 변화가 있을 때
            }

            override fun afterTextChanged(arg0: Editable) {
                // 입력이 끝났을 때
            }


            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                // 입력하기 전에

            }

        })

    }
    fun nickNameCheckPattern(nickName: String): Boolean {

        var match = Pattern.compile(nicknamePattern).matcher(nickName);
        if (match.find()) {
            chkFlag = true
        } else {
            chkFlag = false
        }
        return chkFlag
    }

}