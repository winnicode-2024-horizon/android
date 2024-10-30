package id.winnicode.horizon.data.remote.mapper

import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.data.remote.response.LoginData

fun LoginData.asLogin(): AuthN = AuthN(
    expiredAt = expiredAt,
    token = token
)