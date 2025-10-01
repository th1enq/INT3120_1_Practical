package com.example.unit4_pathway3_replyapp.ui.state

import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType

/**
 * Sealed class representing different screen states in the Reply app.
 * This replaces boolean flags with type-safe state representation.
 */
sealed class ReplyScreenState {
    
    /**
     * Home screen state showing list of emails
     */
    data class Home(
        val currentMailbox: MailboxType = MailboxType.Inbox
    ) : ReplyScreenState()
    
    /**
     * Detail screen state showing a specific email
     */
    data class EmailDetail(
        val email: Email,
        val previousMailbox: MailboxType = MailboxType.Inbox
    ) : ReplyScreenState()
    
    /**
     * Loading state while fetching data
     */
    object Loading : ReplyScreenState()
    
    /**
     * Error state when something goes wrong
     */
    data class Error(
        val message: String,
        val previousState: ReplyScreenState? = null
    ) : ReplyScreenState()
}

/**
 * Extension functions for ReplyScreenState
 */
fun ReplyScreenState.isShowingHomepage(): Boolean = this is ReplyScreenState.Home

fun ReplyScreenState.getCurrentMailbox(): MailboxType = when (this) {
    is ReplyScreenState.Home -> currentMailbox
    is ReplyScreenState.EmailDetail -> previousMailbox
    is ReplyScreenState.Loading -> MailboxType.Inbox
    is ReplyScreenState.Error -> previousState?.getCurrentMailbox() ?: MailboxType.Inbox
}

fun ReplyScreenState.getCurrentEmail(): Email? = when (this) {
    is ReplyScreenState.EmailDetail -> email
    else -> null
}