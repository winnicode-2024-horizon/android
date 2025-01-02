package id.winnicode.horizon.data.remote.mapper

import id.winnicode.horizon.data.remote.response.LoginData
import id.winnicode.horizon.data.remote.response.NewsItem
import id.winnicode.horizon.data.remote.response.ProfileData
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.News
import id.winnicode.horizon.model.UserProfile

fun LoginData.asLogin(): AuthN = AuthN(
    expiredAt = expiredAt,
    token = token
)

fun NewsItem.asNews(isBookmarked: Boolean): News = News(
    publishedAt = publishedAt,
    author = author,
    imageUrl =imageUrl,
    description = description,
    id = id,
    title = title,
    url = url,
    content = content,
    comments = comments,
    isBookmarked = isBookmarked,
    category = category
)

fun ProfileData.asProfile(): UserProfile = UserProfile(
    firstName = firstName,
    lastName = lastName,
    email = email,
    username = username
)