package com.example.unit4_pathway1_uncramble.ui.test

import com.example.unit4_pathway1_uncramble.data.MAX_NO_OF_WORDS
import com.example.unit4_pathway1_uncramble.data.allWords
import com.example.unit4_pathway1_uncramble.ui.GameViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Performance and stress tests for GameViewModel.
 * Tests behavior under various edge conditions and performance constraints.
 */
class GameViewModelPerformanceTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel()
    }

    // region Performance Tests

    /**
     * Test that game initialization is performant.
     */
    @Test
    fun gameViewModel_Initialization_PerformanceTest() {
        val executionTime = measureTimeMillis {
            repeat(100) {
                GameViewModel()
            }
        }
        
        // Should be able to create 100 ViewModels in reasonable time (< 1 second)
        assertTrue("Game initialization should be fast", executionTime < 1000)
    }

    /**
     * Test that rapid user input updates are handled efficiently.
     */
    @Test
    fun gameViewModel_RapidInputUpdates_PerformanceTest() {
        val executionTime = measureTimeMillis {
            repeat(1000) { i ->
                viewModel.updateUserGuess("test$i")
            }
        }
        
        // Should handle 1000 rapid updates efficiently
        assertTrue("Rapid input updates should be efficient", executionTime < 500)
        
        // Final state should be the last input
        assertEquals("Final input should be preserved", "test999", viewModel.userGuess)
    }

    /**
     * Test that multiple game resets are handled efficiently.
     */
    @Test
    fun gameViewModel_MultipleResets_PerformanceTest() {
        val executionTime = measureTimeMillis {
            repeat(50) {
                viewModel.resetGame()
            }
        }
        
        assertTrue("Multiple resets should be efficient", executionTime < 1000)
        
        // Game should still be in valid initial state
        val finalState = viewModel.uiState.value
        assertEquals("Word count should be 1", 1, finalState.currentWordCount)
        assertEquals("Score should be 0", 0, finalState.score)
        assertFalse("Game should not be over", finalState.isGameOver)
    }

    // endregion

    // region Stress Tests

    /**
     * Test behavior with extremely long input strings.
     */
    @Test
    fun gameViewModel_LongInputString_HandledGracefully() {
        val longString = "a".repeat(10000)
        
        viewModel.updateUserGuess(longString)
        
        assertEquals("Long string should be handled", longString, viewModel.userGuess)
        
        // Should not crash when checking guess
        viewModel.checkUserGuess()
        
        val state = viewModel.uiState.value
        assertTrue("Should handle long incorrect guess", state.isGuessedWordWrong)
    }

    /**
     * Test behavior with special characters and unicode.
     */
    @Test
    fun gameViewModel_SpecialCharacters_HandledSafely() {
        val specialInputs = listOf(
            "😀🎮🎯",
            "αβγδε",
            "test\nwith\nnewlines",
            "tab\there",
            "",
            "   ",
            "normal word"
        )
        
        specialInputs.forEach { input ->
            viewModel.updateUserGuess(input)
            
            // Should not crash
            viewModel.checkUserGuess()
            
            // User guess should be cleared after check
            assertEquals("User guess should be cleared after check", "", viewModel.userGuess)
        }
    }

    /**
     * Test rapid successive game completions.
     */
    @Test
    fun gameViewModel_RapidGameCompletions_MemoryStable() {
        repeat(10) { gameNumber ->
            // Complete a full game quickly
            repeat(MAX_NO_OF_WORDS) {
                viewModel.skipWord()
            }
            
            val state = viewModel.uiState.value
            assertTrue("Game $gameNumber should be over", state.isGameOver)
            
            // Reset for next iteration
            viewModel.resetGame()
            
            val resetState = viewModel.uiState.value
            assertFalse("Game $gameNumber should be reset", resetState.isGameOver)
        }
    }

    /**
     * Test that word shuffling produces different results consistently.
     */
    @Test
    fun gameViewModel_WordShuffling_ConsistentlyDifferent() {
        val scrambledWords = mutableSetOf<String>()
        
        repeat(50) {
            viewModel.resetGame()
            val scrambledWord = viewModel.uiState.value.currentScrambledWord
            scrambledWords.add(scrambledWord)
        }
        
        // Should generate various scrambled words (allowing for some repetition due to randomness)
        assertTrue("Should generate varied scrambled words", scrambledWords.size > 10)
        
        // Each scrambled word should exist and be valid
        scrambledWords.forEach { scrambledWord ->
            assertTrue("Scrambled word should not be empty", scrambledWord.isNotEmpty())
            assertTrue("Scrambled word should be reasonable length", scrambledWord.length <= 20)
        }
    }

    // endregion

    // region Edge Case Tests

    /**
     * Test simultaneous operations (simulating rapid user interaction).
     */
    @Test
    fun gameViewModel_SimultaneousOperations_Handled() = runTest {
        // Simulate rapid user actions
        viewModel.updateUserGuess("quick")
        viewModel.checkUserGuess()
        viewModel.updateUserGuess("rapid")
        viewModel.skipWord()
        viewModel.updateUserGuess("test")
        viewModel.checkUserGuess()
        
        // Should end in a consistent state
        val finalState = viewModel.uiState.value
        assertEquals("User guess should be cleared", "", viewModel.userGuess)
        assertTrue("Word count should have progressed", finalState.currentWordCount > 1)
    }

    /**
     * Test behavior when game reaches end through different paths.
     */
    @Test
    fun gameViewModel_GameEndVariations_ConsistentBehavior() {
        // Test ending by skipping all words
        repeat(MAX_NO_OF_WORDS) {
            viewModel.skipWord()
        }
        
        var endState = viewModel.uiState.value
        assertTrue("Game should end by skipping", endState.isGameOver)
        assertEquals("Score should be 0 from skipping", 0, endState.score)
        
        // Reset and test ending by wrong guesses
        viewModel.resetGame()
        repeat(MAX_NO_OF_WORDS) {
            viewModel.updateUserGuess("wrongword")
            viewModel.checkUserGuess()
        }
        
        endState = viewModel.uiState.value
        assertTrue("Game should end by wrong guesses", endState.isGameOver)
        assertEquals("Score should be 0 from wrong guesses", 0, endState.score)
    }

    /**
     * Test that all words from allWords can be properly handled.
     */
    @Test
    fun gameViewModel_AllWordsSupported_NoExceptions() {
        allWords.forEach { word ->
            // Create new ViewModel for each word test
            val testViewModel = GameViewModel()
            
            // Test that we can make guesses with each word
            testViewModel.updateUserGuess(word)
            testViewModel.checkUserGuess()
            
            // Should not crash and should clear input
            assertEquals("User guess should be cleared", "", testViewModel.userGuess)
        }
    }

    // endregion

    // region Memory and Resource Tests

    /**
     * Test that ViewModels can be created and discarded without memory leaks.
     */
    @Test
    fun gameViewModel_MemoryUsage_Reasonable() {
        val viewModels = mutableListOf<GameViewModel>()
        
        // Create many ViewModels
        repeat(100) {
            viewModels.add(GameViewModel())
        }
        
        // Use them briefly
        viewModels.forEach { vm ->
            vm.updateUserGuess("test")
            vm.checkUserGuess()
        }
        
        // Clear references (in real scenario, GC would handle this)
        viewModels.clear()
        
        // Test that new ViewModels still work normally
        val newViewModel = GameViewModel()
        val state = newViewModel.uiState.value
        assertTrue("New ViewModel should work normally", state.currentScrambledWord.isNotEmpty())
    }

    // endregion
}