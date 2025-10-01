package com.example.unit4_pathway3_replyapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.unit4_pathway3_replyapp.R
import com.example.unit4_pathway3_replyapp.data.MailboxType

/**
 * Navigation helper class containing navigation-related data and configurations.
 */
data class NavigationItemContent(
    val mailboxType: MailboxType,
    val icon: ImageVector,
    val text: String
)

/**
 * Object containing navigation constants and helper functions.
 */
object NavigationHelper {
    
    /**
     * Get standard navigation items for the app
     */
    @Composable
    fun getNavigationItems(): List<NavigationItemContent> {
        return listOf(
            NavigationItemContent(
                mailboxType = MailboxType.Inbox,
                icon = Icons.Default.Inbox,
                text = stringResource(id = R.string.tab_inbox)
            ),
            NavigationItemContent(
                mailboxType = MailboxType.Sent,
                icon = Icons.AutoMirrored.Filled.Send,
                text = stringResource(id = R.string.tab_sent)
            ),
            NavigationItemContent(
                mailboxType = MailboxType.Drafts,
                icon = Icons.Default.Drafts,
                text = stringResource(id = R.string.tab_drafts)
            ),
            NavigationItemContent(
                mailboxType = MailboxType.Spam,
                icon = Icons.Default.Report,
                text = stringResource(id = R.string.tab_spam)
            )
        )
    }
    
    /**
     * Get navigation item by mailbox type
     */
    @Composable
    fun getNavigationItem(mailboxType: MailboxType): NavigationItemContent? {
        return getNavigationItems().find { it.mailboxType == mailboxType }
    }
}