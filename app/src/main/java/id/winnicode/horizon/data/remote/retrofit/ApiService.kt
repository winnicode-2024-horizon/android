package id.winnicode.horizon.data.remote.retrofit

import id.winnicode.horizon.data.remote.response.LoginResponse
import id.winnicode.horizon.data.remote.response.NewsResponse
import id.winnicode.horizon.data.remote.response.ProfileResponse
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.model.CommentRequest
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

//  Authentication
    @POST("/api/users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("/api/users/logout")
    suspend fun logout(
        @Header("Authorization") authToken: String
    ):RegisterResponse

//  News
    @Headers("Content-Type: application/json")
    @GET("/api/news")
    suspend fun getNews(
        @Header("Authorization") authToken: String
    ): NewsResponse
//  Search News
    @Headers("Content-Type: application/json")
    @GET("/api/news/search")
    suspend fun searchNews(
        @Header("Authorization") authToken: String,
        @Query("q") query: String
    ): NewsResponse
//  Bookmarks News
    @Headers("Content-Type: application/json")
    @GET("/api/users/bookmarks")
    suspend fun getBookmarks(
        @Header("Authorization") authToken: String
    ): NewsResponse

    @POST("/api/users/bookmarks")
    suspend fun addBookmark(
        @Header("Authorization") authToken: String,
        @Query("id") id: Int
    ): RegisterResponse

    @DELETE("/api/users/bookmarks")
    suspend fun deleteBookmark(
        @Header("Authorization") authToken: String,
        @Query("id") id: Int
    ): RegisterResponse

//  User Profile
    @Headers("Content-Type: application/json")
    @GET("/api/users/profile")
    suspend fun getUserProfile(
        @Header("Authorization") authToken: String,
    ): ProfileResponse

//Comment
    @POST("/api/news/comment")
    suspend fun commentNew(
        @Header("Authorization") authToken: String,
        @Query("newsId") id: Int,
        @Body comment: CommentRequest
    ) : RegisterResponse
}