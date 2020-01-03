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
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.android.synthetic.main.activity_login_nickname.*

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


    private fun initview() {

        nicknameEditText.filters = arrayOf(
            InputFilter.LengthFilter(12)
        )


        button setOnDebounceClickListener {

            val nickname = nicknameEditText.text.toString()

            if(nickname.isBlank()) {
                confirmTextView.text = "닉네임을 입력해주세요"
                return@setOnDebounceClickListener
            }


            lifecycleScope.launchWhenStarted {
                kotlin.runCatching {
                    NetworkClient.userService.checkDuplicateNickname(nickname)
                }.onSuccess {
                    confirmTextView.text = ""
                    startActivity(CardRegisterActivity.newIntent(this@LoginNicknameActivity,userName,nicknameEditText.text.toString()))
                }.onFailure {
//                    confirmTextView.text = ""
//                    startActivity(CardRegisterActivity.newIntent(this@LoginNicknameActivity,userName,nicknameEditText.text.toString()))
                    confirmTextView.text = "중복되는 닉네임입니다"
                }
            }
        }

    }
}


