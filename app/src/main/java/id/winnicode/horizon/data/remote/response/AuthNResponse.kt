package id.winnicode.horizon.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class LoginData(

	@field:SerializedName("expired_at")
	val expiredAt: String,

	@field:SerializedName("token")
	val token: String
)

data class RegisterResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
