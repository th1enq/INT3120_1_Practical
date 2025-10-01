package com.example.unit4_pathway3_replyapp.ui.state

import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType

/**
 * Sealed class representing different UI actions that can be performed in the Reply app.
 * This provides type safety and exhaustive when statements for handling user interactions.
 */
sealed class ReplyUiAction {
    
    /**
     * Navigation related actions
     */
    data class NavigateToMailbox(val mailboxType: MailboxType) : ReplyUiAction()
    data class NavigateToEmailDetail(val email: Email) : ReplyUiAction()
    object NavigateBack : ReplyUiAction()
    
    /**
     * Email management actions
     */
    data class MarkEmailAsRead(val emailId: Long, val isRead: Boolean) : ReplyUiAction()
    data class MoveEmailToMailbox(val emailId: Long, val targetMailbox: MailboxType) : ReplyUiAction()
    data class DeleteEmail(val emailId: Long) : ReplyUiAction()
    data class ReplyToEmail(val emailId: Long) : ReplyUiAction()
    data class ReplyAllToEmail(val emailId: Long) : ReplyUiAction()
    data class ContinueComposing(val emailId: Long) : ReplyUiAction()
    
    /**
     * UI state actions
     */
    object RefreshEmails : ReplyUiAction()
    object ResetToHomeScreen : ReplyUiAction()
}