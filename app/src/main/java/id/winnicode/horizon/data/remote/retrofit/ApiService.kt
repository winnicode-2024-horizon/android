package id.winnicode.horizon.data.remote.retrofit

import id.winnicode.horizon.data.remote.response.LoginResponse
import id.winnicode.horizon.data.remote.response.NewsResponse
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @GET("/api/news")
    suspend fun getNews(
        @Header("Authorization") authToken: String
    ): NewsResponse

    @Headers("Content-Type: application/json")
    @GET("/api/news/search")
    suspend fun searchNews(
        @Header("Authorization") authToken: String,
        @Query("q") query: String
    ): NewsResponse


}