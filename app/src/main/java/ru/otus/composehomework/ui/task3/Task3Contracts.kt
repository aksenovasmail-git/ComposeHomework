package ru.otus.composehomework.ui.task3

import ru.otus.composehomework.data.FormResult

/**
 * Contracts для MVI паттерна (Model-View-Intent)
 * 
 * Содержит все контракты между View и ViewModel:
 * - State - единое состояние UI
 * - Intent - действия пользователя
 * - UiState - состояния выполнения операций
 */

/**
 * Единое состояние UI (Model в MVI)
 */
data class Task3State(
    val name: String = "",
    val email: String = "",
    val message: String = "",
    val uiState: UiState = UiState.Idle,
    val validationErrors: ValidationErrors? = null
)

/**
 * Действия пользователя (Intent в MVI)
 */
sealed class Task3Intent {
    data class NameChanged(val name: String) : Task3Intent()
    data class EmailChanged(val email: String) : Task3Intent()
    data class MessageChanged(val message: String) : Task3Intent()
    object SubmitClicked : Task3Intent()
    object RetryClicked : Task3Intent()
    object ClearClicked : Task3Intent()
}

/**
 * Состояния выполнения операций
 */
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val result: FormResult) : UiState()
    data class Error(val message: String) : UiState()
    data class ValidationError(val errors: ValidationErrors) : UiState()
}

/**
 * Ошибки валидации полей формы
 */
data class ValidationErrors(
    val errors: Map<FieldType, String>
)

/**
 * Типы полей формы для валидации
 */
enum class FieldType {
    NAME,
    EMAIL,
    MESSAGE
}
