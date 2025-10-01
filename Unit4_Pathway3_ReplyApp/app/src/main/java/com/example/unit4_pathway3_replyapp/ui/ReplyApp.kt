package com.example.unit4_pathway3_replyapp.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit4_pathway3_replyapp.ui.state.ReplyUiAction
import com.example.unit4_pathway3_replyapp.ui.utils.toNavigationType

/**
 * Main app composable with improved navigation type handling and action-based interactions.
 */
@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: ReplyViewModel = viewModel()
    val replyUiState = viewModel.uiState.collectAsState().value
    
    // Use extension function for cleaner navigation type determination
    val navigationType = windowSize.toNavigationType()
    
    ReplyHomeScreen(
        navigationType = navigationType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType ->
            viewModel.handleAction(ReplyUiAction.NavigateToMailbox(mailboxType))
        },
        onEmailCardPressed = { email ->
            viewModel.handleAction(ReplyUiAction.NavigateToEmailDetail(email))
        },
        onDetailScreenBackPressed = {
            viewModel.handleAction(ReplyUiAction.NavigateBack)
        },
        onErrorDismissed = {
            viewModel.clearError()
        },
        onRetry = {
            viewModel.handleAction(ReplyUiAction.RefreshEmails)
        },
        modifier = modifier
    )
}
