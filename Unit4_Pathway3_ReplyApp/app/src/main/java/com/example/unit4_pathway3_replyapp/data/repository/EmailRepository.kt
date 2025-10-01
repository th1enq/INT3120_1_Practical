package com.example.unit4_pathway3_replyapp.data.repository

import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing email data operations.
 * Provides an abstraction layer between data sources and the UI layer.
 */
interface EmailRepository {
    /**
     * Get all emails grouped by mailbox type
     */
    suspend fun getAllEmailsByMailbox(): Map<MailboxType, List<Email>>
    
    /**
     * Get emails for a specific mailbox
     */
    suspend fun getEmailsByMailbox(mailboxType: MailboxType): List<Email>
    
    /**
     * Get a specific email by ID
     */
    suspend fun getEmailById(emailId: Long): Email?
    
    /**
     * Get the default email (first inbox email)
     */
    suspend fun getDefaultEmail(): Email
    
    /**
     * Get emails as a flow for reactive updates
     */
    fun getEmailsFlow(): Flow<Map<MailboxType, List<Email>>>
    
    /**
     * Mark email as read/unread
     */
    suspend fun markEmailAsRead(emailId: Long, isRead: Boolean): Result<Unit>
    
    /**
     * Move email to different mailbox
     */
    suspend fun moveEmailToMailbox(emailId: Long, targetMailbox: MailboxType): Result<Unit>
    
    /**
     * Delete email
     */
    suspend fun deleteEmail(emailId: Long): Result<Unit>
}