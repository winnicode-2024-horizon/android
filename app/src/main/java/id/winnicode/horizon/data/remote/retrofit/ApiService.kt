package id.winnicode.horizon.data.remote.retrofit

import id.winnicode.horizon.data.remote.response.LoginResponse
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("/api/news")
    fun getNews(
        @Header("Authorization") authToken: String
    ): LoginResponse


}