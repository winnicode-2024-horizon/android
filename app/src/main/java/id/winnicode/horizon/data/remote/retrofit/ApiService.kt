package id.winnicode.horizon.data.remote.retrofit

import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.RegisterRequest
import id.winnicode.horizon.data.remote.response.LoginResponse
import id.winnicode.horizon.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/api/users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}