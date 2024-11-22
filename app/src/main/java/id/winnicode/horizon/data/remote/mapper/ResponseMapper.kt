package id.winnicode.horizon.data.remote.mapper

import id.winnicode.horizon.data.remote.response.LoginData
import id.winnicode.horizon.data.remote.response.NewsItem
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.News

fun LoginData.asLogin(): AuthN = AuthN(
    expiredAt = expiredAt,
    token = token
)

fun NewsItem.asNews(): News = News(
    publishedAt = publishedAt,
    author = author,
    imageUrl =imageUrl,
    description = description,
    id = id,
    title = title,
    url = url,
    content = content
)