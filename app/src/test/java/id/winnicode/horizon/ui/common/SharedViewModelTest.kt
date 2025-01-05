package id.winnicode.horizon.ui.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.CommentItem
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.remote.response.UserComment
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.CommentRequest
import id.winnicode.horizon.model.News
import id.winnicode.horizon.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@Suppress("UNREACHABLE_CODE")
@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sharedViewModel: SharedViewModel
    private val userPreferences: UserPreferences = mock()
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)

        // Inisialisasi SharedViewModel dengan dependencies yang sudah di-mock
        sharedViewModel = SharedViewModel(userPreferences, userRepository)
    }

    @Test
    fun `test deleteSession success`() = runTest {
        // Act: Panggil fungsi deleteSession
        sharedViewModel.deleteSession()

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verifikasi bahwa logout dipanggil pada userPreferences
        verify(userPreferences).logout()
    }

    @Test
    fun `test logout success`() = runTest {
        // Arrange: Siapkan response yang diharapkan untuk logout
        val response = RegisterResponse("success", "Logged out successfully")
        `when`(userRepository.logout("token")).thenReturn(flowOf(response))

        // Act: Panggil fungsi logout
        sharedViewModel.logout("token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dengan response yang benar
        assert(sharedViewModel.uiState.value is UiState.Success)
        val result = (sharedViewModel.uiState.value as UiState.Success).data
        assert(response == result)
    }

    @Test
    fun `test fetchUserProfile success`() = runTest {
        // Arrange: Siapkan response profil pengguna
        val userProfile = UserProfile("John", "Doe", "john.doe@example.com", "Bio")
        `when`(userRepository.fetchUserProfile("token")).thenReturn(flowOf(userProfile))

        // Act: Panggil fungsi fetchUserProfile
        sharedViewModel.fetchUserProfile("token")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan profileUiState menjadi data profil yang benar
        assert(userProfile  == sharedViewModel.profileUiState.value)
    }

    @Test
    fun `test fetchUserProfile data empty`() = runTest {
        // Arrange: Simulasi error pada fetchUserProfile
        val data = UserProfile("", "", "", "")
        `when`(userRepository.fetchUserProfile("token")).thenReturn(flowOf(data))

        // Act: Panggil fungsi fetchUserProfile
        sharedViewModel.fetchUserProfile("token")

        testDispatcher.scheduler.advanceUntilIdle()

        val result = (sharedViewModel.profileUiState)
        assert(result.value.email.isEmpty())
    }

    @Test
    fun `test commentNew success`() = runTest {
        // Arrange: Siapkan request komentar dan response yang diharapkan
        val commentRequest = CommentRequest("Great article!")
        val response = RegisterResponse("success", "Comment added successfully")
        `when`(userRepository.comment("token", 1, commentRequest)).thenReturn(flowOf(response))

        // Act: Panggil fungsi commentNew
        sharedViewModel.commentNew("token", 1, commentRequest)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dengan response yang benar
        assert(sharedViewModel.uiState.value is UiState.Success)
        val result = (sharedViewModel.uiState.value as UiState.Success).data
        assert(response == result)
    }

    @Test
    fun `test fetchNewsById success`() = runTest {
        // Arrange: Siapkan response berita berdasarkan ID
        val new = News(
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
        `when`(userRepository.fetchNewsById("token", 1)).thenReturn(flowOf(new))

        // Act: Panggil fungsi fetchNewsById
        sharedViewModel.fetchNewsById("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan newsUiState menjadi Success dengan data berita yang benar
        assert(sharedViewModel.newsUiState.value is UiState.Success)
        val result = (sharedViewModel.newsUiState.value as UiState.Success).data
        assert(new == result)
    }

    @Test
    fun `test fetchNewsById data empty`() = runTest {
        // Arrange: Simulasi error pada fetchNewsById
        val data =  News(
        publishedAt = "",
        author = "",
        imageUrl = "",
        description = "",
        id = -1,
        title = "",
        url = "",
        content = "",
        category = "",
        comments = listOf(
            CommentItem(
                id = -1,
                comment = "",
                user = UserComment(
                    username = "",
                    firstName = "",
                    lastName = ""
                )
            )
        )
    )
        `when`(userRepository.fetchNewsById("token", 1)).thenReturn(flowOf(data))

        // Act: Panggil fungsi fetchNewsById
        sharedViewModel.fetchNewsById("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan newsUiState menjadi Error dengan pesan yang benar
        println(sharedViewModel.newsUiState.value)
        assert(sharedViewModel.newsUiState.value is UiState.Success)
        val result = (sharedViewModel.newsUiState.value as UiState.Success).data
        assert(result.publishedAt.isEmpty())
    }
}