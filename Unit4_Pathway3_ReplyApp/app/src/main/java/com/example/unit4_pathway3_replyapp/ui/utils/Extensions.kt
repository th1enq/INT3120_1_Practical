package com.example.unit4_pathway3_replyapp.ui.utils

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.unit4_pathway3_replyapp.data.Account
import com.example.unit4_pathway3_replyapp.data.Email

/**
 * Extension functions for improving code readability and reducing boilerplate.
 */

/**
 * Extension function to determine navigation type based on window size
 */
fun WindowWidthSizeClass.toNavigationType(): ReplyNavigationType {
    return when (this) {
        WindowWidthSizeClass.Compact -> ReplyNavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> ReplyNavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> ReplyNavigationType.BOTTOM_NAVIGATION
    }
}

/**
 * Extension function to get full name of an Account
 */
@Composable
fun Account.getFullName(): String {
    return "${stringResource(firstName)} ${stringResource(lastName)}"
}

/**
 * Extension function to get email subject as string
 */
@Composable
fun Email.getSubjectString(): String {
    return stringResource(subject)
}

/**
 * Extension function to get email body as string
 */
@Composable
fun Email.getBodyString(): String {
    return stringResource(body)
}

/**
 * Extension function to get email creation time as string
 */
@Composable
fun Email.getCreatedAtString(): String {
    return stringResource(createdAt)
}

/**
 * Extension function to get truncated email body for preview
 */
@Composable
fun Email.getBodyPreview(maxLength: Int = ReplyConstants.EMAIL_PREVIEW_MAX_CHARACTERS): String {
    val fullBody = getBodyString()
    return if (fullBody.length <= maxLength) {
        fullBody
    } else {
        "${fullBody.take(maxLength)}..."
    }
}

/**
 * Extension function to check if an email is in a specific mailbox
 */
fun Email.isInMailbox(mailboxType: com.example.unit4_pathway3_replyapp.data.MailboxType): Boolean {
    return this.mailbox == mailboxType
}

/**
 * Extension function to check if navigation type supports detail view
 */
fun ReplyNavigationType.supportsDetailView(): Boolean {
    return this == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
}

/**
 * Extension function to check if navigation type is compact
 */
fun ReplyNavigationType.isCompact(): Boolean {
    return this == ReplyNavigationType.BOTTOM_NAVIGATION
}