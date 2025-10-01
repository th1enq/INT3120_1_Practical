package com.example.unit4_pathway3_replyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit4_pathway3_replyapp.data.Email
import com.example.unit4_pathway3_replyapp.data.MailboxType
import com.example.unit4_pathway3_replyapp.data.repository.EmailRepository
import com.example.unit4_pathway3_replyapp.data.repository.LocalEmailRepository
import com.example.unit4_pathway3_replyapp.ui.state.ReplyScreenState
import com.example.unit4_pathway3_replyapp.ui.state.ReplyUiAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Enhanced ViewModel with repository pattern, action-based state updates,
 * and comprehensive error handling.
 */
class ReplyViewModel(
    private val emailRepository: EmailRepository = LocalEmailRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReplyUiState())
    val uiState: StateFlow<ReplyUiState> = _uiState.asStateFlow()

    init {
        initializeUIState()
        observeEmailUpdates()
    }

    /**
     * Handle UI actions with proper error handling and state management
     */
    fun handleAction(action: ReplyUiAction) {
        when (action) {
            is ReplyUiAction.NavigateToMailbox -> navigateToMailbox(action.mailboxType)
            is ReplyUiAction.NavigateToEmailDetail -> navigateToEmailDetail(action.email)
            is ReplyUiAction.NavigateBack -> navigateBack()
            is ReplyUiAction.MarkEmailAsRead -> markEmailAsRead(action.emailId, action.isRead)
            is ReplyUiAction.MoveEmailToMailbox -> moveEmailToMailbox(action.emailId, action.targetMailbox)
            is ReplyUiAction.DeleteEmail -> deleteEmail(action.emailId)
            is ReplyUiAction.ReplyToEmail -> replyToEmail(action.emailId)
            is ReplyUiAction.ReplyAllToEmail -> replyAllToEmail(action.emailId)
            is ReplyUiAction.ContinueComposing -> continueComposing(action.emailId)
            is ReplyUiAction.RefreshEmails -> refreshEmails()
            is ReplyUiAction.ResetToHomeScreen -> resetToHomeScreen()
        }
    }

    private fun initializeUIState() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                
                val mailboxes = emailRepository.getAllEmailsByMailbox()
                val defaultEmail = emailRepository.getDefaultEmail()
                
                _uiState.update {
                    it.copy(
                        mailboxes = mailboxes,
                        currentSelectedEmail = defaultEmail,
                        screenState = ReplyScreenState.Home(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load emails: ${e.message}"
                    )
                }
            }
        }
    }

    private fun observeEmailUpdates() {
        emailRepository.getEmailsFlow()
            .catch { e ->
                _uiState.update {
                    it.copy(errorMessage = "Failed to update emails: ${e.message}")
                }
            }
            .onEach { mailboxes ->
                _uiState.update { it.copy(mailboxes = mailboxes) }
            }
            .launchIn(viewModelScope)
    }

    private fun navigateToEmailDetail(email: Email) {
        _uiState.update {
            it.copy(
                currentSelectedEmail = email,
                screenState = ReplyScreenState.EmailDetail(
                    email = email,
                    previousMailbox = it.currentMailbox
                )
            )
        }
    }

    private fun navigateBack() {
        _uiState.update { currentState ->
            when (val screenState = currentState.screenState) {
                is ReplyScreenState.EmailDetail -> {
                    val defaultEmail = currentState.mailboxes[screenState.previousMailbox]?.firstOrNull()
                        ?: currentState.currentSelectedEmail
                    
                    currentState.copy(
                        currentSelectedEmail = defaultEmail,
                        screenState = ReplyScreenState.Home(screenState.previousMailbox)
                    )
                }
                is ReplyScreenState.Error -> {
                    currentState.copy(
                        screenState = screenState.previousState ?: ReplyScreenState.Home(),
                        errorMessage = null
                    )
                }
                else -> currentState.copy(
                    screenState = ReplyScreenState.Home(),
                    errorMessage = null
                )
            }
        }
    }

    private fun navigateToMailbox(mailboxType: MailboxType) {
        _uiState.update {
            it.copy(screenState = ReplyScreenState.Home(mailboxType))
        }
    }

    private fun markEmailAsRead(emailId: Long, isRead: Boolean) {
        viewModelScope.launch {
            emailRepository.markEmailAsRead(emailId, isRead)
                .onFailure { e ->
                    _uiState.update {
                        it.copy(errorMessage = "Failed to mark email: ${e.message}")
                    }
                }
        }
    }

    private fun moveEmailToMailbox(emailId: Long, targetMailbox: MailboxType) {
        viewModelScope.launch {
            emailRepository.moveEmailToMailbox(emailId, targetMailbox)
                .onFailure { e ->
                    _uiState.update {
                        it.copy(errorMessage = "Failed to move email: ${e.message}")
                    }
                }
        }
    }

    private fun deleteEmail(emailId: Long) {
        viewModelScope.launch {
            emailRepository.deleteEmail(emailId)
                .onFailure { e ->
                    _uiState.update {
                        it.copy(errorMessage = "Failed to delete email: ${e.message}")
                    }
                }
        }
    }

    private fun replyToEmail(emailId: Long) {
        // In a real app, this would open a compose screen
        _uiState.update {
            it.copy(errorMessage = null) // Clear any previous errors
        }
    }

    private fun replyAllToEmail(emailId: Long) {
        // In a real app, this would open a compose screen with all recipients
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    private fun continueComposing(emailId: Long) {
        // In a real app, this would open the draft for editing
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    private fun refreshEmails() {
        initializeUIState()
    }

    private fun resetToHomeScreen() {
        navigateBack()
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Legacy methods for backward compatibility - these will be removed after UI refactoring
    @Deprecated("Use handleAction(NavigateToEmailDetail) instead")
    fun updateDetailsScreenStates(email: Email) {
        handleAction(ReplyUiAction.NavigateToEmailDetail(email))
    }

    @Deprecated("Use handleAction(NavigateBack) instead")
    fun resetHomeScreenStates() {
        handleAction(ReplyUiAction.NavigateBack)
    }

    @Deprecated("Use handleAction(NavigateToMailbox) instead")
    fun updateCurrentMailbox(mailboxType: MailboxType) {
        handleAction(ReplyUiAction.NavigateToMailbox(mailboxType))
    }
}