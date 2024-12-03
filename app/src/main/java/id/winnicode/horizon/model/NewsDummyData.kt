package id.winnicode.horizon.model

import id.winnicode.horizon.data.remote.response.CommentItem
import id.winnicode.horizon.data.remote.response.UserComment

object NewsDummyData {
    val newsList = listOf(
        News(
            publishedAt = "2024-11-05T08:00:00Z",
            author = "John Doe",
            imageUrl = "https://i.postimg.cc/MKLS8wxg/ezgif-5-0c331b9b43.png",
            description = "Berita terkini mengenai perkembangan ekonomi global.",
            id = 1,
            title = "Kesempatan Karir di Bidang Kecerdasan Buatan: Apa yang Perlu Dipersiapkan?",
            url = "https://example.com/news/1",
            content = "Ekonomi dunia diperkirakan tumbuh 3% pada tahun 2024, didorong oleh investasi teknologi dan infrastruktur.",
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
        ),
        News(
            publishedAt = "2024-11-04T18:00:00Z",
            author = "Jane Smith",
            imageUrl = "https://i.postimg.cc/MKLS8wxg/ezgif-5-0c331b9b43.png",
            description = "Pemerintah meluncurkan program baru untuk pendidikan digital.",
            id = 2,
            title = "Pemerintah Luncurkan Program Pendidikan Digital",
            url = "https://example.com/news/2",
            content = "Program ini bertujuan untuk meningkatkan keterampilan digital di kalangan pelajar dan tenaga kerja muda.",
            comments = listOf(
                CommentItem(
                    id = 1,
                    comment = "This is a game changer for education!",
                    user = UserComment(
                        username = "alice93",
                        firstName = "Alice",
                        lastName = "Alice Johnson"
                    )
                ),
                CommentItem(
                    id = 2,
                    comment = "Hope this helps the younger generation.",
                    user = UserComment(
                        username = "bob456",
                        firstName = "Bob",
                        lastName = "Robert Williams"
                    )
                )
            )
        ),
        News(
            publishedAt = "2024-11-03T12:30:00Z",
            author = "Sarah Lee",
            imageUrl = "https://i.postimg.cc/ZKnSgnkr/ezgif-6-1ae3feb0fc.png",
            description = "Tim nasional sepak bola Indonesia meraih kemenangan di kualifikasi Piala Dunia.",
            id = 3,
            title = "Timnas Indonesia Menang Telak di Kualifikasi Piala Dunia",
            url = "https://example.com/news/3",
            content = "Indonesia berhasil mengalahkan tim lawan dengan skor 4-1 dalam pertandingan terakhir kualifikasi.",
            comments = listOf(
                CommentItem(
                    id = 1,
                    comment = "Great match, Indonesia played brilliantly!",
                    user = UserComment(
                        username = "soccerfan88",
                        firstName = "Michael",
                        lastName = "Michael Lee"
                    )
                )
            )
        ),
        News(
            publishedAt = "2024-11-02T15:45:00Z",
            author = "Michael Tan",
            imageUrl = "https://i.postimg.cc/s2dCvJMc/ezgif-6-8fbf084e56.png",
            description = "NASA mengumumkan misi baru ke Mars untuk mencari kehidupan.",
            id = 4,
            title = "NASA Rencanakan Misi Baru ke Mars",
            url = "https://example.com/news/4",
            content = "Misi ini bertujuan untuk mengumpulkan sampel tanah Mars dan mencari tanda-tanda kehidupan mikroba.",
            comments = listOf(
                CommentItem(
                    id = 1,
                    comment = "This mission is going to change everything we know about Mars!",
                    user = UserComment(
                        username = "spacefan2024",
                        firstName = "Emma",
                        lastName = "Emma Watson"
                    )
                )
            )
        ),
        News(
            publishedAt = "2024-11-01T10:00:00Z",
            author = "David Kim",
            imageUrl = "https://example.com/images/news5.jpg",
            description = "Inovasi terbaru di dunia teknologi membawa perubahan besar di sektor industri.",
            id = 5,
            title = "Teknologi Industri Mengalami Terobosan Baru",
            url = "https://example.com/news/5",
            content = "Penggunaan AI dan robotika dalam pabrik-pabrik semakin meluas, meningkatkan efisiensi dan mengurangi biaya produksi.",
            comments = listOf(
                CommentItem(
                    id = 1,
                    comment = "AI is definitely the future of industry!",
                    user = UserComment(
                        username = "techlover77",
                        firstName = "David",
                        lastName = "David Brown"
                    )
                )
            )
        )
    )

    val comments = listOf(
        CommentItem(
            id = 1,
            comment = "Good Information!",
            user = UserComment(
                username = "johnny",
                firstName = "John",
                lastName = "John Smith"
            )
        ),
        CommentItem(
            id = 2,
            comment = "Great post! Keep it up!",
            user = UserComment(
                username = "sarah21",
                firstName = "Sarah",
                lastName = "Connor"
            )
        ),
        CommentItem(
            id = 3,
            comment = "Very informative, thank you!",
            user = UserComment(
                username = "mike007",
                firstName = "Mike",
                lastName = "Johnson"
            )
        ),
        CommentItem(
            id = 4,
            comment = "This helped me a lot, much appreciated!",
            user = UserComment(
                username = "laura_doe",
                firstName = "Laura",
                lastName = "Doe"
            )
        ),
        CommentItem(
            id = 5,
            comment = "Interesting perspective!",
            user = UserComment(
                username = "adam_smith",
                firstName = "Adam",
                lastName = "Smith"
            )
        ),
        CommentItem(
            id = 6,
            comment = "I disagree with some points, but overall great read.",
            user = UserComment(
                username = "emma_lyn",
                firstName = "Emma",
                lastName = "Lynch"
            )
        ),
        CommentItem(
            id = 7,
            comment = "Could you elaborate more on the topic?",
            user = UserComment(
                username = "chris123",
                firstName = "Chris",
                lastName = "Evans"
            )
        ),
        CommentItem(
            id = 8,
            comment = "This is really insightful!",
            user = UserComment(
                username = "kelly_wong",
                firstName = "Kelly",
                lastName = "Wong"
            )
        ),
        CommentItem(
            id = 9,
            comment = "I have learned a lot from this article.",
            user = UserComment(
                username = "james_lee",
                firstName = "James",
                lastName = "Lee"
            )
        ),
        CommentItem(
            id = 10,
            comment = "Thanks for sharing!",
            user = UserComment(
                username = "linda_jane",
                firstName = "Linda",
                lastName = "Jane"
            )
        ),
        CommentItem(
            id = 11,
            comment = "Could you add more examples?",
            user = UserComment(
                username = "oscar_wilde",
                firstName = "Oscar",
                lastName = "Wilde"
            )
        ),
        CommentItem(
            id = 12,
            comment = "Amazing read, I totally agree with you.",
            user = UserComment(
                username = "luis_felix",
                firstName = "Luis",
                lastName = "Felix"
            )
        ),
        CommentItem(
            id = 13,
            comment = "The information provided is top-notch.",
            user = UserComment(
                username = "mia_rose",
                firstName = "Mia",
                lastName = "Rose"
            )
        ),
        CommentItem(
            id = 14,
            comment = "Good job! Keep writing.",
            user = UserComment(
                username = "matthew_griffin",
                firstName = "Matthew",
                lastName = "Griffin"
            )
        ),
        CommentItem(
            id = 15,
            comment = "This was a very helpful post.",
            user = UserComment(
                username = "sophie_clark",
                firstName = "Sophie",
                lastName = "Clark"
            )
        ),
        CommentItem(
            id = 16,
            comment = "I would love to read more from you.",
            user = UserComment(
                username = "john_doe",
                firstName = "John",
                lastName = "Doe"
            )
        )
    )

    val keyword = listOf(
        SearchSuggestion(keyword = "Teknologi"),
        SearchSuggestion(keyword = "Pendidikan"),
        SearchSuggestion(keyword = "Ekonomi"),
        SearchSuggestion(keyword = "Politik"),
        SearchSuggestion(keyword = "Olahraga"),
        SearchSuggestion(keyword = "Kesehatan"),
        SearchSuggestion(keyword = "Lingkungan"),
        SearchSuggestion(keyword = "COVID-19"),
        SearchSuggestion(keyword = "Film"),
        SearchSuggestion(keyword = "Ekspansi Bisnis"),
        SearchSuggestion(keyword = "Startup"),
        SearchSuggestion(keyword = "Perekonomian Global"),
        SearchSuggestion(keyword = "Festival Film"),

        // Topik Berita Ekonomi dan Bisnis
        SearchSuggestion(keyword = "Pasar Saham"),
        SearchSuggestion(keyword = "Inflasi"),
        SearchSuggestion(keyword = "Investasi"),
        SearchSuggestion(keyword = "Mikroekonomi"),
        SearchSuggestion(keyword = "Cryptocurrency"),
        SearchSuggestion(keyword = "Reksa Dana"),
        SearchSuggestion(keyword = "Fintech"),
        SearchSuggestion(keyword = "Perdagangan Global"),
        SearchSuggestion(keyword = "Perusahaan Unicorn"),
        SearchSuggestion(keyword = "Startup Teknologi"),

        // Topik Politik
        SearchSuggestion(keyword = "Pemilu 2024"),
        SearchSuggestion(keyword = "Kebijakan Publik"),
        SearchSuggestion(keyword = "Perang Dunia"),
        SearchSuggestion(keyword = "Hubungan Internasional"),
        SearchSuggestion(keyword = "Hak Asasi Manusia"),
        SearchSuggestion(keyword = "Demokrasi"),
        SearchSuggestion(keyword = "Isu Lingkungan Politik"),
        SearchSuggestion(keyword = "Perubahan Iklim dan Politik"),

        // Topik Olahraga
        SearchSuggestion(keyword = "Piala Dunia 2024"),
        SearchSuggestion(keyword = "Olimpiade 2024"),
        SearchSuggestion(keyword = "Sepak Bola"),
        SearchSuggestion(keyword = "Basketball"),
        SearchSuggestion(keyword = "Formula 1"),
        SearchSuggestion(keyword = "Sepak Bola Internasional"),
        SearchSuggestion(keyword = "Performa Atlet"),
        SearchSuggestion(keyword = "Kejuaraan Dunia Tenis"),

        // Topik Kesehatan
        SearchSuggestion(keyword = "Vaksin COVID-19"),
        SearchSuggestion(keyword = "Pandemi Global"),
        SearchSuggestion(keyword = "Gizi Seimbang"),
        SearchSuggestion(keyword = "Kanker"),
        SearchSuggestion(keyword = "Kesehatan Mental"),
        SearchSuggestion(keyword = "Penyakit Menular"),
        SearchSuggestion(keyword = "Penyakit Jantung"),
        SearchSuggestion(keyword = "Obesitas"),
        SearchSuggestion(keyword = "Terapi Genetik"),

        // Topik Lingkungan dan Alam
        SearchSuggestion(keyword = "Pemanasan Global"),
        SearchSuggestion(keyword = "Energi Terbarukan"),
        SearchSuggestion(keyword = "Konservasi Alam"),
        SearchSuggestion(keyword = "Perubahan Iklim"),
        SearchSuggestion(keyword = "Daur Ulang"),
        SearchSuggestion(keyword = "Pencemaran Udara"),
        SearchSuggestion(keyword = "Kerusakan Hutan"),
        SearchSuggestion(keyword = "Polusi Laut"),
        SearchSuggestion(keyword = "Keanekaragaman Hayati"),

        // Topik Budaya dan Hiburan
        SearchSuggestion(keyword = "Musik Populer"),
        SearchSuggestion(keyword = "Film Terbaru"),
        SearchSuggestion(keyword = "Industri Musik"),
        SearchSuggestion(keyword = "Buku Terlaris"),
        SearchSuggestion(keyword = "Penerbitan Buku"),
        SearchSuggestion(keyword = "Kesenian Tradisional"),
        SearchSuggestion(keyword = "Festival Musik"),
        SearchSuggestion(keyword = "Seni Visual"),
        SearchSuggestion(keyword = "Teater"),

        // Topik Sosial dan Masyarakat
        SearchSuggestion(keyword = "Kesetaraan Gender"),
        SearchSuggestion(keyword = "Kesenjangan Sosial"),
        SearchSuggestion(keyword = "Pendidikan Anak"),
        SearchSuggestion(keyword = "Pemberdayaan Perempuan"),
        SearchSuggestion(keyword = "Krisis Pengungsi"),
        SearchSuggestion(keyword = "Pemuda dan Aktivisme"),
        SearchSuggestion(keyword = "Sosial Media"),
        SearchSuggestion(keyword = "Budaya Masyarakat"),
        SearchSuggestion(keyword = "Protes Sosial"),

        // Teknologi dan Inovasi
        SearchSuggestion(keyword = "Artificial Intelligence (AI)"),
        SearchSuggestion(keyword = "Blockchain"),
        SearchSuggestion(keyword = "5G"),
        SearchSuggestion(keyword = "Internet of Things (IoT)"),
        SearchSuggestion(keyword = "Augmented Reality (AR)"),
        SearchSuggestion(keyword = "Virtual Reality (VR)"),
        SearchSuggestion(keyword = "Big Data"),
        SearchSuggestion(keyword = "Machine Learning"),
        SearchSuggestion(keyword = "Robotika"),

        // Topik Sejarah dan Budaya
        SearchSuggestion(keyword = "Sejarah Dunia"),
        SearchSuggestion(keyword = "Perang Dunia Kedua"),
        SearchSuggestion(keyword = "Revolusi Industri"),
        SearchSuggestion(keyword = "Kehidupan Kuno"),
        SearchSuggestion(keyword = "Arkeologi"),
        SearchSuggestion(keyword = "Masyarakat Adat"),
        SearchSuggestion(keyword = "Peradaban Mesir Kuno"),
        SearchSuggestion(keyword = "Artefak Sejarah"),

        // Keuangan dan Perbankan
        SearchSuggestion(keyword = "Suku Bunga Bank"),
        SearchSuggestion(keyword = "Pinjaman Mikro"),
        SearchSuggestion(keyword = "Asuransi"),
        SearchSuggestion(keyword = "Keuangan Pribadi"),
        SearchSuggestion(keyword = "Kredit Rumah"),
        SearchSuggestion(keyword = "Digital Banking"),
        SearchSuggestion(keyword = "Bank Sentral"),
        SearchSuggestion(keyword = "Finansial Inklusif"),

        // Teknologi dan Perangkat
        SearchSuggestion(keyword = "Smartphone Terbaru"),
        SearchSuggestion(keyword = "Laptop dan Gadget"),
        SearchSuggestion(keyword = "Perangkat Wearable"),
        SearchSuggestion(keyword = "Kamera Digital"),
        SearchSuggestion(keyword = "Smart Home"),
        SearchSuggestion(keyword = "Perangkat VR"),
        SearchSuggestion(keyword = "Teknologi Wearable"),
        SearchSuggestion(keyword = "Konsol Game")
    )
}