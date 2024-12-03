package id.winnicode.horizon.data.repository

import id.winnicode.horizon.data.remote.mapper.asLogin
import id.winnicode.horizon.data.remote.mapper.asNews
import id.winnicode.horizon.data.remote.mapper.asProfile
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.remote.retrofit.ApiService
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.CommentRequest
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.model.News
import id.winnicode.horizon.model.NewsDummyData
import id.winnicode.horizon.model.RegisterRequest
import id.winnicode.horizon.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository(
    private val api: ApiService,
): UserRepository {
    private val News = mutableListOf<News>()

    init {
        if (News.isEmpty()){
            NewsDummyData.newsList.forEach{
                News.add(it)
            }
        }
    }
    override suspend fun fetchNews(token: String): Flow<List<News>> {
        return flow {
            val newBookmark = api.getBookmarks("Bearer $token").data.news.map { bookmark ->
                bookmark.id
            }.toSet()
            val news = api.getNews("Bearer $token").data.news.map {newsItem ->
                val isBookmarked = newBookmark.contains(newsItem.id)
                newsItem.asNews(isBookmarked)
            }
            emit(news)
        }
    }

    override suspend fun fetchNewsById(token: String, NewsId: Int): Flow<News> {
        return flow {
            val newBookmark = api.getBookmarks("Bearer $token").data.news.map { bookmark ->
                bookmark.id
            }.toSet()
            val news = api.getNews("Bearer $token").data.news.map {newsItem ->
                val isBookmarked = newBookmark.contains(newsItem.id)
                newsItem.asNews(isBookmarked)
            }
            val newsDetail = news.find {
                it.id == NewsId
            }
            newsDetail?.let { emit(it) }
        }
    }

    override suspend fun searchNews(query: String, token: String): Flow<List<News>> {
        return flow {
            val newBookmark = api.getBookmarks("Bearer $token").data.news.map { bookmark ->
                bookmark.id
            }.toSet()
            val news = api.searchNews("Bearer $token", query).data.news.map {newsItem ->
                val isBookmarked = newBookmark.contains(newsItem.id)
                newsItem.asNews(isBookmarked)
            }
            emit(news)
        }
    }

    override suspend fun register(
        request: RegisterRequest
    ): Flow<RegisterResponse> {
        return flow {
            val register = api.register(request)
            emit(register)
        }
    }

    override suspend fun fetchDummyNewsById(NewsId: Int): News {
        return News.first {
            it.id == NewsId
        }
    }

    override suspend fun login(request: LoginRequest): Flow<AuthN> {
        return flow {
            val login = api.login(request).data?.asLogin()
            if (login != null) {
                emit(login)
            }
        }
    }

    override suspend fun logout(token: String): Flow<RegisterResponse> {
        return flow {
            val logout = api.logout("Bearer $token")
            emit(logout)
        }
    }

    override suspend fun fetchBookmarkNews(token: String): Flow<List<News>> {
        return flow {
            val newBookmark = api.getBookmarks("Bearer $token").data.news.map { bookmark ->
                bookmark.id
            }.toSet()
            val news = api.getBookmarks("Bearer $token").data.news.map {newsItem ->
                val isBookmarked = newBookmark.contains(newsItem.id)
                newsItem.asNews(isBookmarked)
            }
            emit(news)
        }
    }

    override suspend fun deleteBookmark(token: String, id: Int): Flow<RegisterResponse> {
        return flow {
            val delete = api.deleteBookmark(authToken = "Bearer $token", id = id)
            emit(delete)
        }
    }

    override suspend fun addBookmark(token: String, id: Int): Flow<RegisterResponse> {
        return flow {
            val add = api.addBookmark(authToken = "Bearer $token", id = id)
            emit(add)
        }
    }

    override suspend fun fetchUserProfile(token: String): Flow<UserProfile> {
        return flow {
            val profile = api.getUserProfile(authToken = "Bearer $token").data.asProfile()
            emit(profile)
        }
    }

    override suspend fun comment(token: String, newsId: Int, request: CommentRequest): Flow<RegisterResponse> {
        return flow {
            val comment = api.commentNew(authToken = "Bearer $token", id = newsId, comment = request)
            emit(comment)
        }
    }
}