package id.winnicode.horizon.model

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
            content = "Ekonomi dunia diperkirakan tumbuh 3% pada tahun 2024, didorong oleh investasi teknologi dan infrastruktur."
        ),
        News(
            publishedAt = "2024-11-04T18:00:00Z",
            author = "Jane Smith",
            imageUrl = "https://i.postimg.cc/MKLS8wxg/ezgif-5-0c331b9b43.png",
            description = "Pemerintah meluncurkan program baru untuk pendidikan digital.",
            id = 2,
            title = "Pemerintah Luncurkan Program Pendidikan Digital",
            url = "https://example.com/news/2",
            content = "Program ini bertujuan untuk meningkatkan keterampilan digital di kalangan pelajar dan tenaga kerja muda."
        ),
        News(
            publishedAt = "2024-11-03T12:30:00Z",
            author = "Sarah Lee",
            imageUrl = "https://i.postimg.cc/ZKnSgnkr/ezgif-6-1ae3feb0fc.png",
            description = "Tim nasional sepak bola Indonesia meraih kemenangan di kualifikasi Piala Dunia.",
            id = 3,
            title = "Timnas Indonesia Menang Telak di Kualifikasi Piala Dunia",
            url = "https://example.com/news/3",
            content = "Indonesia berhasil mengalahkan tim lawan dengan skor 4-1 dalam pertandingan terakhir kualifikasi."
        ),
        News(
            publishedAt = "2024-11-02T15:45:00Z",
            author = "Michael Tan",
            imageUrl = "https://i.postimg.cc/s2dCvJMc/ezgif-6-8fbf084e56.png",
            description = "NASA mengumumkan misi baru ke Mars untuk mencari kehidupan.",
            id = 4,
            title = "NASA Rencanakan Misi Baru ke Mars",
            url = "https://example.com/news/4",
            content = "Misi ini bertujuan untuk mengumpulkan sampel tanah Mars dan mencari tanda-tanda kehidupan mikroba."
        ),
        News(
            publishedAt = "2024-11-01T10:00:00Z",
            author = "David Kim",
            imageUrl = "https://example.com/images/news5.jpg",
            description = "Inovasi terbaru di dunia teknologi membawa perubahan besar di sektor industri.",
            id = 5,
            title = "Teknologi Industri Mengalami Terobosan Baru",
            url = "https://example.com/news/5",
            content = "Penggunaan AI dan robotika dalam pabrik-pabrik semakin meluas, meningkatkan efisiensi dan mengurangi biaya produksi."
        ),
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