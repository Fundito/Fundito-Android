package com.fundito.fundito.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import kotlinx.android.synthetic.main.activity_login_nickname.*
import kotlinx.coroutines.flow.collect
import reactivecircus.flowbinding.android.widget.textChanges
import java.util.regex.Pattern

class LoginNicknameActivity : AppCompatActivity() {

    companion object {

        private const val ARG_NAME = "ARG_NAME"

        fun newIntent(context: Context, name: String): Intent {
            return Intent(context, LoginNicknameActivity::class.java).apply {
                putExtra(ARG_NAME, name)
            }
        }
    }

    private val userName: String
        get() = intent?.getStringExtra(ARG_NAME) ?: ""

    private val nicknamePattern: String = "^[A-Za-z[0-9]]{2,12}$" // 영문, 숫자

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_nickname)

        username.text = buildSpannedString {
            append("안녕하세요 ")
            color(resources.getColor(R.color.dark_navy)){append(userName)}
            append("님!")
        }

        makeController()
        initview()


    }

    private fun makeController() {
        // 이번에는 kotlin extensions를 이용해서 개발 진행
        button.setOnClickListener {
            val nickname = nicknameEditText.text.toString()
            // 빈 칸이 있으면 안되므로 빈 칸 체크
            if (nickname.isEmpty()) {
                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@LoginNicknameActivity, CardRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nickNameCheckPattern(nickName: String): Boolean {
        val match = Pattern.compile(nicknamePattern).matcher(nickName)
        return match.matches()
    }

    private fun initview() {

        nicknameEditText.filters = arrayOf(
            InputFilter.LengthFilter(12)
        )

        lifecycleScope.launchWhenStarted {
            nicknameEditText
                .textChanges()
                .collect {nickname->
                    if(nickNameCheckPattern(nickname.toString())) {
                        confirmTextView.text = "사용 가능합니다"
                        confirmTextView.setTextColor(resources.getColor(R.color.blueberry_two))
                    }else {
                        confirmTextView.text = "2-12글자 사이로만 입력해주세요"
                        confirmTextView.setTextColor(resources.getColor(R.color.coral))
                    }
                }
        }

        button setOnDebounceClickListener {
            lifecycleScope.launchWhenStarted {
                kotlin.runCatching {
                    //TODO
                }.onSuccess {
                    startActivity(CardRegisterActivity.newIntent(this@LoginNicknameActivity,userName,nicknameEditText.text.toString()))
                }.onFailure {

                }
            }
        }

    }
}


