package cn.woolsen.ourstu.ui.login

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.woolsen.ourstu.R
import cn.woolsen.ourstu.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.state.observe(this) { state ->
            if (state == LOGIN) {
                binding.usernameLayout.setHint(R.string.hint_login_account)
                binding.password.imeOptions = EditorInfo.IME_ACTION_GO
            } else if (state == REGISTER) {
                binding.usernameLayout.setHint(R.string.hint_register_account)
                binding.password.imeOptions = EditorInfo.IME_ACTION_NEXT
            }
        }
        viewModel.codeCountdown.observe(this) { countdown ->
            if (countdown == 0) {
                binding.sendCode.setText(R.string.send_code)
                binding.sendCode.isClickable = true
            } else {
                binding.sendCode.text = getString(R.string.send_code_countdown, countdown)
                binding.sendCode.isClickable = false
            }
        }
        viewModel.message.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LOGIN = 1
        const val REGISTER = 2
    }

    override fun onBackPressed() {
        if (viewModel.state.value == REGISTER) {
            viewModel.state.value = LOGIN
            return
        }
        super.onBackPressed()
    }

}