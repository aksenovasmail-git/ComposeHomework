package ru.otus.composehomework.ui.task3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.otus.composehomework.data.Repository

/**
 * ViewModel для задания 3 (MVI паттерн).
 * Вся бизнес-логика уже реализована.
 * Студенту нужно только подключить её к Compose UI.
 * 
 * MVI (Model-View-Intent) паттерн:
 * - State - единое состояние UI (определено в Task3Contracts.kt)
 * - Intent - действия пользователя (определено в Task3Contracts.kt)
 * - ViewModel обрабатывает Intent и обновляет State
 */
class Task3ViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {
    
    private val _state = MutableStateFlow(Task3State())
    val state: StateFlow<Task3State> = _state.asStateFlow()
    
    /**
     * Обработка Intent от UI
     */
    fun handleIntent(intent: Task3Intent) {
        when (intent) {
            is Task3Intent.NameChanged -> handleNameChanged(intent.name)
            is Task3Intent.EmailChanged -> handleEmailChanged(intent.email)
            is Task3Intent.MessageChanged -> handleMessageChanged(intent.message)
            is Task3Intent.SubmitClicked -> handleSubmit()
            is Task3Intent.RetryClicked -> handleRetry()
            is Task3Intent.ClearClicked -> handleClear()
        }
    }
    
    private fun handleNameChanged(name: String) {
        _state.update { currentState ->
            currentState.copy(
                name = name,
                validationErrors = clearValidationError(currentState.validationErrors, FieldType.NAME)
            )
        }
        updateUiStateAfterValidation()
    }
    
    private fun handleEmailChanged(email: String) {
        _state.update { currentState ->
            currentState.copy(
                email = email,
                validationErrors = clearValidationError(currentState.validationErrors, FieldType.EMAIL)
            )
        }
        updateUiStateAfterValidation()
    }
    
    private fun handleMessageChanged(message: String) {
        _state.update { currentState ->
            currentState.copy(
                message = message,
                validationErrors = clearValidationError(currentState.validationErrors, FieldType.MESSAGE)
            )
        }
        updateUiStateAfterValidation()
    }
    
    private fun handleRetry() {
        _state.update { currentState ->
            currentState.copy(
                uiState = UiState.Idle,
                validationErrors = null
            )
        }
    }
    
    private fun handleClear() {
        _state.update { Task3State() }
    }
    
    private fun handleSubmit() {
        val errors = validateFields(_state.value)
        if (errors != null) {
            _state.update { currentState ->
                currentState.copy(
                    validationErrors = errors,
                    uiState = UiState.ValidationError(errors)
                )
            }
            return
        }
        
        _state.update { currentState ->
            currentState.copy(
                validationErrors = null,
                uiState = UiState.Loading
            )
        }
        
        viewModelScope.launch {
            val result = repository.submitForm(
                name = _state.value.name,
                email = _state.value.email,
                message = _state.value.message
            )
            
            if (result.isSuccess) {
                _state.update { currentState ->
                    currentState.copy(
                        uiState = UiState.Success(result.getOrNull()!!)
                    )
                }
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        uiState = UiState.Error(
                            result.exceptionOrNull()?.message ?: "Неизвестная ошибка"
                        )
                    )
                }
            }
        }
    }
    
    private fun validateFields(currentState: Task3State): ValidationErrors? {
        val errors = mutableMapOf<FieldType, String>()
        
        if (currentState.name.isBlank()) {
            errors[FieldType.NAME] = "Имя не может быть пустым"
        }
        
        if (currentState.email.isBlank()) {
            errors[FieldType.EMAIL] = "Email не может быть пустым"
        } else if (!isValidEmail(currentState.email)) {
            errors[FieldType.EMAIL] = "Некорректный формат email"
        }
        
        if (currentState.message.isBlank()) {
            errors[FieldType.MESSAGE] = "Сообщение не может быть пустым"
        } else if (currentState.message.length < 10) {
            errors[FieldType.MESSAGE] = "Сообщение должно содержать минимум 10 символов"
        }
        
        return if (errors.isEmpty()) null else ValidationErrors(errors)
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    private fun clearValidationError(
        currentErrors: ValidationErrors?,
        fieldType: FieldType
    ): ValidationErrors? {
        val errors = currentErrors?.errors?.toMutableMap() ?: return null
        errors.remove(fieldType)
        return if (errors.isEmpty()) null else ValidationErrors(errors)
    }
    
    private fun updateUiStateAfterValidation() {
        _state.update { currentState ->
            if (currentState.uiState is UiState.ValidationError) {
                val newUiState = if (currentState.validationErrors == null) {
                    UiState.Idle
                } else {
                    UiState.ValidationError(currentState.validationErrors)
                }
                currentState.copy(uiState = newUiState)
            } else {
                currentState
            }
        }
    }
}
