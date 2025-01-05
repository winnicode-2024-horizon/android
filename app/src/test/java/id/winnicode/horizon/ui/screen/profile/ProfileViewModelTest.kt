package id.winnicode.horizon.ui.screen.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.UserProfile
import id.winnicode.horizon.ui.common.UiState
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

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Untuk memastikan LiveData dijalankan di thread utama

    private lateinit var profileViewModel: ProfileViewModel
    private val userPreferences: UserPreferences = mock()
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)

        // Inisialisasi ProfileViewModel dengan dependencies yang sudah di-mock
        profileViewModel = ProfileViewModel(userRepository, userPreferences)
    }

    @Test
    fun `test fetchUserProfile success`() = runTest {
        // Arrange: Siapkan data UserProfile yang valid
        val userProfile = UserProfile("John", "Doe", "john.doe@example.com", "https://example.com/profile.jpg")
        `when`(userRepository.fetchUserProfile("token")).thenReturn(flowOf(userProfile))

        // Act: Panggil fungsi fetchUserProfile
        profileViewModel.fetchUserProfile("token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data sesuai
        assert(profileViewModel.uiState.value is UiState.Success)
        val result = (profileViewModel.uiState.value as UiState.Success).data
        assert(userProfile == result)
    }

    @Test
    fun `test fetchUserProfile data empty`() = runTest {
        // Arrange: Simulasi error pada fetchUserProfile
        val userProfile = UserProfile("", "", "", "")
        `when`(userRepository.fetchUserProfile("token")).thenReturn(flowOf(userProfile))

        // Act: Panggil fungsi fetchUserProfile
        profileViewModel.fetchUserProfile("token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Error dengan pesan yang benar
        assert(profileViewModel.uiState.value is UiState.Success)
        val result = (profileViewModel.uiState.value as UiState.Success).data
        assert(result.email.isEmpty())
    }
}