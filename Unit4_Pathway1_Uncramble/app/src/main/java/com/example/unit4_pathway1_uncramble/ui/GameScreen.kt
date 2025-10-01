/**
 * GameScreen.kt
 * 
 * Contains the main UI composables for the Unscramble word game.
 * Provides the game interface including word display, input fields, buttons, and dialogs.
 */
package com.example.unit4_pathway1_uncramble.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit4_pathway1_uncramble.R
import com.example.unit4_pathway1_uncramble.ui.theme.Unit4_Pathway1_UncrambleTheme

/**
 * Object containing UI constants used throughout the game screen
 */
private object GameScreenConstants {
    /** Default card elevation for game components */
    val CARD_ELEVATION = 5.dp
    
    /** Button text size */
    val BUTTON_TEXT_SIZE = 16.sp
    
    /** Status card padding */
    val STATUS_CARD_PADDING = 20.dp
    
    /** Score text padding */
    val SCORE_TEXT_PADDING = 8.dp
    
    /** Word count badge horizontal padding */
    val WORD_COUNT_HORIZONTAL_PADDING = 10.dp
    
    /** Word count badge vertical padding */
    val WORD_COUNT_VERTICAL_PADDING = 4.dp
}

/**
 * Main game screen composable that displays the unscramble word game interface.
 * 
 * This composable manages the overall layout of the game including:
 * - App title
 * - Game layout with scrambled word and input field
 * - Action buttons (Submit and Skip)
 * - Score display
 * - End game dialog
 * 
 * @param gameViewModel The ViewModel managing game state and logic
 */
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameTitle()
        
        GameLayout(
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            wordCount = gameUiState.currentWordCount,
            userGuess = gameViewModel.userGuess,
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            currentScrambledWord = gameUiState.currentScrambledWord,
            isGuessWrong = gameUiState.isGuessedWordWrong,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        
        GameActionButtons(
            onSubmit = { gameViewModel.checkUserGuess() },
            onSkip = { gameViewModel.skipWord() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding)
        )

        GameStatus(
            score = gameUiState.score, 
            modifier = Modifier.padding(GameScreenConstants.STATUS_CARD_PADDING)
        )

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

/**
 * Displays the game title at the top of the screen.
 */
@Composable
private fun GameTitle() {
    Text(
        text = stringResource(R.string.app_name),
        style = typography.titleLarge,
    )
}

/**
 * Displays the action buttons (Submit and Skip) for the game.
 * 
 * @param onSubmit Callback when the submit button is clicked
 * @param onSkip Callback when the skip button is clicked
 * @param modifier Modifier for styling the button container
 */
@Composable
private fun GameActionButtons(
    onSubmit: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSubmit
        ) {
            Text(
                text = stringResource(R.string.submit),
                fontSize = GameScreenConstants.BUTTON_TEXT_SIZE
            )
        }

        OutlinedButton(
            onClick = onSkip,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.skip),
                fontSize = GameScreenConstants.BUTTON_TEXT_SIZE
            )
        }
    }
}

/**
 * Displays the current game score in a card format.
 * 
 * @param score The current game score to display
 * @param modifier Modifier for styling the score card
 */
@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(GameScreenConstants.SCORE_TEXT_PADDING)
        )
    }
}

/**
 * Main game layout containing the scrambled word, input field, and game instructions.
 * 
 * @param currentScrambledWord The scrambled word to display
 * @param wordCount Current word number in the game
 * @param isGuessWrong Whether the current guess is incorrect
 * @param userGuess Current user input
 * @param onUserGuessChanged Callback when user input changes
 * @param onKeyboardDone Callback when user presses Done on keyboard
 * @param modifier Modifier for styling the layout
 */
@Composable
fun GameLayout(
    currentScrambledWord: String,
    wordCount: Int,
    isGuessWrong: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = GameScreenConstants.CARD_ELEVATION)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            WordCountBadge(
                wordCount = wordCount,
                modifier = Modifier.align(Alignment.End)
            )
            
            ScrambledWordDisplay(currentScrambledWord = currentScrambledWord)
            
            GameInstructions()
            
            UserGuessInput(
                userGuess = userGuess,
                isGuessWrong = isGuessWrong,
                onUserGuessChanged = onUserGuessChanged,
                onKeyboardDone = onKeyboardDone
            )
        }
    }
}

/**
 * Displays the word count badge in the top-right corner.
 * 
 * @param wordCount Current word number
 * @param modifier Modifier for styling
 */
@Composable
private fun WordCountBadge(
    wordCount: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .clip(shapes.medium)
            .background(colorScheme.surfaceTint)
            .padding(
                horizontal = GameScreenConstants.WORD_COUNT_HORIZONTAL_PADDING,
                vertical = GameScreenConstants.WORD_COUNT_VERTICAL_PADDING
            ),
        text = stringResource(R.string.word_count, wordCount),
        style = typography.titleMedium,
        color = colorScheme.onPrimary
    )
}

/**
 * Displays the scrambled word.
 * 
 * @param currentScrambledWord The scrambled word to display
 */
@Composable
private fun ScrambledWordDisplay(currentScrambledWord: String) {
    Text(
        text = currentScrambledWord,
        style = typography.displayMedium
    )
}

/**
 * Displays the game instructions.
 */
@Composable
private fun GameInstructions() {
    Text(
        text = stringResource(R.string.instructions),
        textAlign = TextAlign.Center,
        style = typography.titleMedium
    )
}

/**
 * Input field for user's word guess.
 * 
 * @param userGuess Current user input
 * @param isGuessWrong Whether the guess is wrong
 * @param onUserGuessChanged Callback when input changes
 * @param onKeyboardDone Callback when Done is pressed
 */
@Composable
private fun UserGuessInput(
    userGuess: String,
    isGuessWrong: Boolean,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit
) {
    OutlinedTextField(
        value = userGuess,
        singleLine = true,
        shape = shapes.large,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
            disabledContainerColor = colorScheme.surface,
        ),
        onValueChange = onUserGuessChanged,
        label = {
            Text(
                text = if (isGuessWrong) {
                    stringResource(R.string.wrong_guess)
                } else {
                    stringResource(R.string.enter_your_word)
                }
            )
        },
        isError = isGuessWrong,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        )
    )
}

/**
 * Dialog displayed when the game ends, showing the final score and options to play again or exit.
 * 
 * @param score The final game score
 * @param onPlayAgain Callback when user chooses to play again
 * @param modifier Modifier for styling the dialog
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            // Allow dismissal by tapping outside or back button
            // This provides a way to close the dialog without making a choice
        },
        title = { 
            Text(text = stringResource(R.string.congratulations)) 
        },
        text = { 
            Text(text = stringResource(R.string.you_scored, score)) 
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    // Safely exit the activity
                    (context as? Activity)?.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    Unit4_Pathway1_UncrambleTheme {
        GameScreen()
    }
}