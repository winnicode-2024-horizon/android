package id.winnicode.horizon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.winnicode.horizon.di.Injection
import id.winnicode.horizon.ui.screen.login.LoginViewModel
import id.winnicode.horizon.ui.screen.register.RegisterViewModel

class ViewModelFactory(
    private val injection: Injection
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                injection.userRepository,
                injection.userPreferences
            )
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                injection.userRepository
            )

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}