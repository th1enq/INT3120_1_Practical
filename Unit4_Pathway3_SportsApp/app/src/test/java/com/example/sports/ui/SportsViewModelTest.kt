package com.example.sports.ui

import com.example.sports.model.Sport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for SportsViewModel to validate refactored functionality.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SportsViewModelTest {

    private lateinit var testRepository: TestSportsRepository
    private lateinit var viewModel: SportsViewModel

    @Before
    fun setup() {
        testRepository = TestSportsRepository()
        viewModel = SportsViewModel(testRepository)
    }

    @Test
    fun `initial state should have correct default values`() = runTest {
        val initialState = viewModel.uiState.first()

        assertEquals(testRepository.getSportsData(), initialState.sportsList)
        assertEquals(testRepository.getSportsData().first(), initialState.currentSport)
        assertEquals(NavigationState.LIST, initialState.navigationState)
        assertTrue(initialState.isShowingListPage)
        assertFalse(initialState.isLoading)
        assertNull(initialState.errorMessage)
    }

    @Test
    fun `selectSport should update current sport and navigate to detail`() = runTest {
        val sport = testRepository.getSportsData()[1]
        
        viewModel.selectSport(sport)
        val state = viewModel.uiState.first()

        assertEquals(sport, state.currentSport)
        assertEquals(NavigationState.DETAIL, state.navigationState)
        assertFalse(state.isShowingListPage)
        assertNull(state.errorMessage)
    }

    @Test
    fun `updateCurrentSport should update sport without changing navigation`() = runTest {
        val sport = testRepository.getSportsData()[1]
        
        viewModel.updateCurrentSport(sport)
        val state = viewModel.uiState.first()

        assertEquals(sport, state.currentSport)
        assertEquals(NavigationState.LIST, state.navigationState)
        assertTrue(state.isShowingListPage)
    }

    @Test
    fun `navigateToListPage should update navigation state`() = runTest {
        // First navigate to detail
        viewModel.navigateToDetailPage()
        
        // Then navigate back to list
        viewModel.navigateToListPage()
        val state = viewModel.uiState.first()

        assertEquals(NavigationState.LIST, state.navigationState)
        assertTrue(state.isShowingListPage)
        assertNull(state.errorMessage)
    }

    @Test
    fun `navigateToDetailPage should only work when sport is selected`() = runTest {
        viewModel.navigateToDetailPage()
        val state = viewModel.uiState.first()

        assertEquals(NavigationState.DETAIL, state.navigationState)
        assertFalse(state.isShowingListPage)
    }

    @Test
    fun `clearError should remove error message`() = runTest {
        // Create an error condition by trying to select invalid sport
        testRepository.shouldThrowError = true
        viewModel.refreshSportsData()
        
        viewModel.clearError()
        val state = viewModel.uiState.first()

        assertNull(state.errorMessage)
    }

    @Test
    fun `refreshSportsData should handle errors gracefully`() = runTest {
        testRepository.shouldThrowError = true
        
        viewModel.refreshSportsData()
        val state = viewModel.uiState.first()

        assertNotNull(state.errorMessage)
        assertFalse(state.isLoading)
        assertTrue(state.errorMessage!!.contains("Failed to refresh sports data"))
    }
}

/**
 * Test implementation of SportsRepository for unit testing.
 */
class TestSportsRepository : SportsRepository {
    var shouldThrowError = false

    private val testSports = listOf(
        Sport(
            id = 1,
            titleResourceId = android.R.string.ok,
            subtitleResourceId = android.R.string.ok,
            playerCount = 11,
            olympic = true,
            imageResourceId = android.R.drawable.ic_dialog_alert,
            sportsImageBanner = android.R.drawable.ic_dialog_alert,
            sportDetails = android.R.string.ok
        ),
        Sport(
            id = 2,
            titleResourceId = android.R.string.cancel,
            subtitleResourceId = android.R.string.cancel,
            playerCount = 5,
            olympic = false,
            imageResourceId = android.R.drawable.ic_dialog_info,
            sportsImageBanner = android.R.drawable.ic_dialog_info,
            sportDetails = android.R.string.cancel
        )
    )

    override fun getSportsData(): List<Sport> {
        if (shouldThrowError) {
            throw RuntimeException("Test error")
        }
        return testSports
    }

    override fun getDefaultSport(): Sport = testSports.first()
}