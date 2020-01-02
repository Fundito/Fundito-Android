package com.fundito.fundito.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fundito.fundito.R
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.android.synthetic.main.activity_login_nickname.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern

class LoginNicknameActivity : AppCompatActivity() {

    companion object {

        private const val ARG_NAME = "ARG_NAME"

        fun newIntent(context: Context, name: String) : Intent {
            return Intent(context, LoginNicknameActivity::class.java).apply {
                putExtra(ARG_NAME,name)
            }
        }
    }

    private val userName: String
    get() = intent?.getStringExtra(ARG_NAME) ?: ""

    val nicknamePattern: String = "^[A-Za-z[0-9]]{2,5}$" // 영문, 숫자
    var chkFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_nickname)

        makeController()
        initview()



        nicknameEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                button.visibility = View.VISIBLE
                if (nickNameCheckPattern(nicknameEditText.text.toString())) {
                    confirmTextView.setText("사용 가능합니다.")
                } else {
                    confirmTextView.setText("2-5글자 사이로만 입력해주세요")
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

    fun makeController() {
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

    fun nickNameCheckPattern(nickName: String): Boolean {

        val match = Pattern.compile(nicknamePattern).matcher(nickName);
        chkFlag = match.find()
        return chkFlag
    }
    private fun initview(){
        lifecycleScope.launch {
            kotlin.runCatching {
                var a = NetworkClient.userService.getUser()
                Timber.e("1")
                name.text =a.name
                Timber.e("2")
                nickName.text = a.nickname
            }
                .onSuccess {
                    Timber.e("success")
                    val intent = Intent(this@LoginNicknameActivity, CardRegisterActivity::class.java)
                    startActivity(intent)
                }
                .onFailure {
                    Timber.e("Fail")
                    Timber.e(it.message.toString())
                }
        }
    }
}


