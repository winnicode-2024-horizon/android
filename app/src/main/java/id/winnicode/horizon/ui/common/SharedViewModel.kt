package id.winnicode.horizon.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.model.AuthN
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class SharedViewModel(
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _userSession: MutableStateFlow<AuthN> = MutableStateFlow(AuthN("","", false))
    val userSession: StateFlow<AuthN>
        get() = _userSession


    init {
        viewModelScope.launch {
            userPreferences.getUserSession()
                .collect { authN ->
                    _userSession.value = authN
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleLogout(expiredAt: String) {
        val expirationTime = Instant.parse(expiredAt)
        val remainingTime = expirationTime.toEpochMilli() - System.currentTimeMillis()

        viewModelScope.launch {
            if (remainingTime > 0) {
                delay(remainingTime)
                logout()
            } else {
                logout()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

}