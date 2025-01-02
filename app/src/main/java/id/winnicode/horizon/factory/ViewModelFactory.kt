package id.winnicode.horizon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.winnicode.horizon.di.Injection
import id.winnicode.horizon.ui.common.SharedViewModel
import id.winnicode.horizon.ui.screen.bookmark.BookmarkViewModel
import id.winnicode.horizon.ui.screen.detail.DetailViewModel
import id.winnicode.horizon.ui.screen.home.HomeViewModel
import id.winnicode.horizon.ui.screen.login.LoginViewModel
import id.winnicode.horizon.ui.screen.profile.ProfileViewModel
import id.winnicode.horizon.ui.screen.register.RegisterViewModel
import id.winnicode.horizon.ui.screen.setting.SettingViewModel

class ViewModelFactory(
    private val injection: Injection
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(SharedViewModel::class.java) -> SharedViewModel(
                injection.userPreferences,
                injection.userRepository
            )

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                injection.userRepository,
                injection.userPreferences
            )
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                injection.userRepository
            )
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                injection.userPreferences,
                injection.userRepository
            )
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(
                injection.userPreferences,
                injection.userRepository
            )
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(
                injection.userPreferences,
                injection.userRepository
            )
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(
                injection.userRepository,
                injection.userPreferences
            )
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(
                injection.userPreferences
            )

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}