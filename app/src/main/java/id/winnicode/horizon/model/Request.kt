package id.winnicode.horizon.model

data class RegisterRequest(
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)