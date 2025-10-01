package com.example.unit4_pathway3_replyapp.data.repository

import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType
import com.example.unit4_pathway3_replyapp.data.local.LocalEmailsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementation of EmailRepository using local data source.
 * Handles email operations and provides reactive data streams.
 */
class LocalEmailRepository : EmailRepository {
    
    // In a real app, this would be backed by a database or network source
    private val _emailsFlow = MutableStateFlow(
        LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }
    )
    
    private val emailsFlow: StateFlow<Map<MailboxType, List<Email>>> = _emailsFlow.asStateFlow()
    
    override suspend fun getAllEmailsByMailbox(): Map<MailboxType, List<Email>> {
        return LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }
    }
    
    override suspend fun getEmailsByMailbox(mailboxType: MailboxType): List<Email> {
        return getAllEmailsByMailbox()[mailboxType] ?: emptyList()
    }
    
    override suspend fun getEmailById(emailId: Long): Email? {
        return LocalEmailsDataProvider.allEmails.find { it.id == emailId }
    }
    
    override suspend fun getDefaultEmail(): Email {
        val inboxEmails = getEmailsByMailbox(MailboxType.Inbox)
        return inboxEmails.firstOrNull() ?: LocalEmailsDataProvider.defaultEmail
    }
    
    override fun getEmailsFlow(): Flow<Map<MailboxType, List<Email>>> {
        return emailsFlow
    }
    
    override suspend fun markEmailAsRead(emailId: Long, isRead: Boolean): Result<Unit> {
        return try {
            // In a real implementation, this would update the email's read status
            // For now, we'll just return success
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun moveEmailToMailbox(emailId: Long, targetMailbox: MailboxType): Result<Unit> {
        return try {
            // In a real implementation, this would move the email to the target mailbox
            // and update the data source
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteEmail(emailId: Long): Result<Unit> {
        return try {
            // In a real implementation, this would delete the email from the data source
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}