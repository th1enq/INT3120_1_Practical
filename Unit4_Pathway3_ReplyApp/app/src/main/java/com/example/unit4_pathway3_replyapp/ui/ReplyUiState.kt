package com.example.unit4_pathway3_replyapp.ui

import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType
import com.example.unit4_pathway3_replyapp.data.local.LocalEmailsDataProvider
import com.example.unit4_pathway3_replyapp.ui.state.ReplyScreenState
import com.example.unit4_pathway3_replyapp.ui.state.getCurrentMailbox
import com.example.unit4_pathway3_replyapp.ui.state.isShowingHomepage

/**
 * Enhanced UI state with better type safety and state management.
 * Uses sealed classes instead of boolean flags for better state representation.
 */
data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val screenState: ReplyScreenState = ReplyScreenState.Home(),
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // Computed properties for backward compatibility and convenience
    val currentMailbox: MailboxType 
        get() = screenState.getCurrentMailbox()
    
    val isShowingHomepage: Boolean 
        get() = screenState.isShowingHomepage()
    
    val currentMailboxEmails: List<Email> 
        get() = mailboxes[currentMailbox] ?: emptyList()
}
