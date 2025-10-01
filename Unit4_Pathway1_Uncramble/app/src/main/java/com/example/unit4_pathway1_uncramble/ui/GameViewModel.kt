package com.example.unit4_pathway1_uncramble.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unit4_pathway1_uncramble.data.MAX_NO_OF_WORDS
import com.example.unit4_pathway1_uncramble.data.SCORE_INCREASE
import com.example.unit4_pathway1_uncramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Unscramble game that manages game state and logic.
 * Handles word selection, shuffling, score tracking, and game progression.
 */
class GameViewModel : ViewModel() {

    companion object {
        /** Minimum number of shuffle attempts to ensure word is scrambled */
        private const val MIN_SHUFFLE_ATTEMPTS = 3
        /** Maximum shuffle attempts to prevent infinite loops */
        private const val MAX_SHUFFLE_ATTEMPTS = 100
    }

    /** Game UI state exposed as StateFlow for UI observation */
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    /** Current user's guess input */
    var userGuess by mutableStateOf("")
        private set

    /** Set of words already used in the current game session */
    private var usedWords: MutableSet<String> = mutableSetOf()
    
    /** The current unscrambled word that the user needs to guess */
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    /**
     * Re-initializes the game data to restart the game.
     * Clears used words and starts with a new scrambled word.
     */
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    /**
     * Updates the user's current guess.
     * Automatically trims whitespace and validates input.
     * 
     * @param guessedWord The new guess from the user
     */
    fun updateUserGuess(guessedWord: String) {
        // Clear error state when user starts typing a new guess
        if (_uiState.value.isGuessedWordWrong && guessedWord.isNotEmpty()) {
            _uiState.update { it.copy(isGuessedWordWrong = false) }
        }
        userGuess = guessedWord.trim()
    }

    /**
     * Checks if the user's guess is correct and updates the game state accordingly.
     * If correct, increases the score and progresses to the next word.
     * If incorrect, shows an error state.
     */
    fun checkUserGuess() {
        // Don't process empty guesses
        if (userGuess.isBlank()) {
            _uiState.update { it.copy(isGuessedWordWrong = true) }
            return
        }
        
        val isCorrect = userGuess.equals(currentWord, ignoreCase = true)
        
        if (isCorrect) {
            val updatedScore = _uiState.value.score + SCORE_INCREASE
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        
        // Clear user input after checking
        updateUserGuess("")
    }

    /**
     * Skips the current word without awarding points and moves to the next word.
     */
    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    /**
     * Updates the game state based on the current progress.
     * If max words reached, ends the game. Otherwise, prepares the next word.
     * 
     * @param updatedScore The new score to set
     */
    private fun updateGameState(updatedScore: Int) {
        val isGameComplete = usedWords.size == MAX_NO_OF_WORDS
        
        _uiState.update { currentState ->
            if (isGameComplete) {
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            } else {
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount + 1,
                    score = updatedScore
                )
            }
        }
    }

    /**
     * Shuffles the characters of a word ensuring the result is different from the original.
     * Uses multiple shuffle attempts with a limit to prevent infinite loops.
     * 
     * @param word The word to shuffle
     * @return The shuffled word, guaranteed to be different from the original
     */
    private fun shuffleCurrentWord(word: String): String {
        if (word.length <= 1) return word
        
        val tempWord = word.toCharArray()
        var shuffledWord: String
        var attempts = 0
        
        do {
            tempWord.shuffle()
            shuffledWord = String(tempWord)
            attempts++
        } while (shuffledWord == word && attempts < MAX_SHUFFLE_ATTEMPTS)
        
        // If we couldn't shuffle effectively, manually swap characters
        if (shuffledWord == word && word.length > 1) {
            val chars = word.toCharArray()
            // Swap first and last characters as fallback
            val temp = chars[0]
            chars[0] = chars[chars.size - 1]
            chars[chars.size - 1] = temp
            shuffledWord = String(chars)
        }
        
        return shuffledWord
    }

    /**
     * Selects a random word from available words and shuffles it.
     * Uses filtering instead of recursion for better performance.
     * 
     * @return The shuffled version of the selected word
     */
    private fun pickRandomWordAndShuffle(): String {
        val availableWords = allWords.filter { !usedWords.contains(it) }
        
        if (availableWords.isEmpty()) {
            // This shouldn't happen in normal gameplay, but handle gracefully
            return ""
        }
        
        currentWord = availableWords.random()
        usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)
    }
}
