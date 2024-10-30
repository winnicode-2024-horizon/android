package id.winnicode.horizon.model

data class AuthN(
    val expiredAt: String,
    val token: String,
    val isLogin: Boolean = false
)
