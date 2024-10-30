package id.winnicode.horizon.data.repository

import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import id.winnicode.horizon.data.remote.mapper.asLogin
import id.winnicode.horizon.data.remote.response.NewsItem
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository(
    private val api: ApiService,
): UserRepository {
    override suspend fun fetchNews(token: String): Flow<NewsItem> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        request: RegisterRequest
    ): Flow<RegisterResponse> {
        return flow {
            val register = api.register(request)
            emit(register)
        }
    }

    override suspend fun login(request: LoginRequest): Flow<AuthN> {
        return flow {
            val login = api.login(request).data?.asLogin()
            if (login != null) {
                emit(login)
            }
        }
    }
}