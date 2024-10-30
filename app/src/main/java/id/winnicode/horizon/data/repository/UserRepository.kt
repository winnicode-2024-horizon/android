package id.winnicode.horizon.data.repository

import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import id.winnicode.horizon.data.remote.response.NewsItem
import id.winnicode.horizon.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchNews(token: String): Flow<NewsItem>

    suspend fun register(request: RegisterRequest): Flow<RegisterResponse>

    suspend fun login(request: LoginRequest): Flow<AuthN>
}