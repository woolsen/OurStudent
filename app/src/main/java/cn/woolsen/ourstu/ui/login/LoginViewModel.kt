package cn.woolsen.ourstu.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.woolsen.ourstu.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val state = MutableLiveData(LoginActivity.LOGIN)
    val codeCountdown = MutableLiveData(0)

    val account = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val code = MutableLiveData<String>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun loginOrRegister() {
        if (state.value == LoginActivity.LOGIN) {
            login()
        } else {
            register()
        }
    }

    private fun login() {
        viewModelScope.launch {
            val account = this@LoginViewModel.account.value
            if (account.isNullOrBlank()) {
                _message.postValue("用户名/邮箱不能为空")
                return@launch
            }
            val password = this@LoginViewModel.password.value
            if (password.isNullOrBlank()) {
                _message.postValue("密码不能为空")
                return@launch
            }
            try {
                repository.login(account, password)
            } catch (e: Exception) {
                e.printStackTrace()
                _message.postValue(e.message)
            }
        }
    }

    private fun register() {

    }

    fun goRegister() {
        state.value = LoginActivity.REGISTER
    }

    fun back() {
        state.value = LoginActivity.LOGIN
    }

    fun sendCode() {
        if (codeCountdown.value != 0) {
            return
        }
        codeCountdown.value = 60
        viewModelScope.launch(Dispatchers.IO) {
            val email = this@LoginViewModel.email.value
            if (email.isNullOrBlank()) {
                _message.postValue("邮箱不能为空")
                return@launch
            }
            try {
                repository.sendCode(email)
                _message.postValue("验证码已发送")
                var count = 60
                while (count-- > 0) {
                    delay(1000)
                    codeCountdown.postValue(count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _message.postValue(e.message)
            }
        }
    }

}