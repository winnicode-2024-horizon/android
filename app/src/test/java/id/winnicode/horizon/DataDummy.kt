package id.winnicode.horizon

import id.winnicode.horizon.data.remote.response.CommentItem
import id.winnicode.horizon.data.remote.response.UserComment
import id.winnicode.horizon.model.News

object DataDummy {

    fun generateDummyNewsResponse(): List<News> {
        val items: MutableList<News> = arrayListOf()
        for (i in 0..100) {
            val news = News(
                publishedAt = "2024-11-05T08:00:00Z",
                author = "John Doe",
                imageUrl = "https://i.postimg.cc/MKLS8wxg/ezgif-5-0c331b9b43.png",
                description = "Berita terkini mengenai perkembangan ekonomi global.",
                id = 1,
                title = "Kesempatan Karir di Bidang Kecerdasan Buatan: Apa yang Perlu Dipersiapkan?",
                url = "https://example.com/news/1",
                content = "Ekonomi dunia diperkirakan tumbuh 3% pada tahun 2024, didorong oleh investasi teknologi dan infrastruktur.",
                category = "General",
                comments = listOf(
                    CommentItem(
                        id = 1,
                        comment = "Good Information!",
                        user = UserComment(
                            username = "johnny",
                            firstName = "John",
                            lastName = "John Smith"
                        )
                    )
                )
            )
            items.add(news)
        }
        return items
    }
}