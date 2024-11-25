package id.winnicode.horizon.data.repository

import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.News
import id.winnicode.horizon.model.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchNews(token: String): Flow<List<News>>
    suspend fun fetchNewsById(token: String, NewsId: Int): Flow<News>

    suspend fun searchNews(query: String, token: String): Flow<List<News>>

    suspend fun register(request: RegisterRequest): Flow<RegisterResponse>
    suspend fun fetchDummyNewsById(NewsId: Int): News

    suspend fun login(request: LoginRequest): Flow<AuthN>

    suspend fun logout(token: String): Flow<RegisterResponse>

}