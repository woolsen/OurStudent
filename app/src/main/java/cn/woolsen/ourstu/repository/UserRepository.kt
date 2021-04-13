package cn.woolsen.ourstu.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor() {

    suspend fun login(account: String, password: String) {
        withContext(Dispatchers.IO) {
            if (account == "admin") {

            }
        }
    }

    suspend fun register(account: String, password: String, email: String, code: String) {
        withContext(Dispatchers.IO) {

        }
    }

    suspend fun sendCode(email: String) {
        withContext(Dispatchers.IO) {

        }
    }

}