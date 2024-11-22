package id.winnicode.horizon.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.model.AuthN
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SharedViewModel(
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthN("","", false)
    )
    val userSession: StateFlow<AuthN>
        get() = _userSession

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun scheduleLogout(expiredAt: String) {
//        val expirationTime = Instant.parse(expiredAt)
//        val remainingTime = expirationTime.toEpochMilli() - System.currentTimeMillis()
//
//        viewModelScope.launch {
//            if (remainingTime > 0) {
//                delay(remainingTime)
//                logout()
//            } else {
//                logout()
//            }
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            userPreferences.logout()
//        }
//    }

}