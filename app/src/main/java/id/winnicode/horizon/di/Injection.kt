package id.winnicode.horizon.di

import android.content.Context
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.prefrences.dataStore
import id.winnicode.horizon.data.remote.retrofit.ApiConfig
import id.winnicode.horizon.data.repository.DefaultUserRepository
import id.winnicode.horizon.data.repository.UserRepository

class Injection(
    context: Context
) {
    private val api = ApiConfig.getApiService()

    val userPreferences = UserPreferences.getInstance(context.dataStore)

    val userRepository: UserRepository = DefaultUserRepository(
        api = api,
    )
}