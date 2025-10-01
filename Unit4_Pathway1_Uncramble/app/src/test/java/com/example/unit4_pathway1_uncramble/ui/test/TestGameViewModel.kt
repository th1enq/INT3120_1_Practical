package com.example.unit4_pathway1_uncramble.ui.test

import com.example.unit4_pathway1_uncramble.data.allWords
import com.example.unit4_pathway1_uncramble.ui.GameViewModel

/**
 * A test-specific extension of GameViewModel that exposes internal state for testing.
 * This allows us to test the game logic more effectively without relying on guessing
 * the correct word from the scrambled version.
 */
class TestGameViewModel : GameViewModel() {
    
    /**
     * Exposes the current unscrambled word for testing purposes.
     * Uses reflection to access the private currentWord field.
     */
    val currentUnscrambledWord: String
        get() {
            return try {
                val field = GameViewModel::class.java.getDeclaredField("currentWord")
                field.isAccessible = true
                field.get(this) as String
            } catch (e: Exception) {
                // Fallback: try to determine the word from the scrambled version
                determineCorrectWordFromScrambled(uiState.value.currentScrambledWord)
            }
        }
    
    /**
     * Helper method to determine the correct word from a scrambled word.
     * This tries to match by length and character composition.
     */
    private fun determineCorrectWordFromScrambled(scrambledWord: String): String {
        val scrambledChars = scrambledWord.toCharArray().sorted()
        
        return allWords.find { word ->
            val wordChars = word.toCharArray().sorted()
            wordChars == scrambledChars
        } ?: ""
    }
    
    /**
     * Forces a specific word to be the current word for testing.
     * This is useful for testing specific scenarios.
     */
    fun setCurrentWordForTesting(word: String) {
        try {
            val field = GameViewModel::class.java.getDeclaredField("currentWord")
            field.isAccessible = true
            field.set(this, word)
            
            // Also update the UI state with the scrambled version
            val scrambledWord = shuffleWord(word)
            val currentState = uiState.value
            _uiState.value = currentState.copy(currentScrambledWord = scrambledWord)
        } catch (e: Exception) {
            // If reflection fails, we can't set the word
            println("Failed to set current word for testing: ${e.message}")
        }
    }
    
    /**
     * Simple word shuffling for testing.
     */
    private fun shuffleWord(word: String): String {
        val chars = word.toCharArray()
        chars.shuffle()
        return String(chars)
    }
    
    /**
     * Provides access to the private _uiState for testing scenarios.
     */
    private val _uiState by lazy {
        try {
            val field = GameViewModel::class.java.getDeclaredField("_uiState")
            field.isAccessible = true
            field.get(this) as kotlinx.coroutines.flow.MutableStateFlow<com.example.unit4_pathway1_uncramble.ui.GameUiState>
        } catch (e: Exception) {
            throw RuntimeException("Could not access _uiState field", e)
        }
    }
}