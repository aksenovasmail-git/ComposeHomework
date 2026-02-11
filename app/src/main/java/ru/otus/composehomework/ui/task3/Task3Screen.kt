package ru.otus.composehomework.ui.task3

import androidx.compose.runtime.Composable

/**
 * Задание 3: Подключение ViewModel и комплексное состояние (MVI паттерн)
 * 
 * Развивайте Task2Screen, подключив ViewModel для управления состоянием.
 * 
 * MVI (Model-View-Intent) паттерн:
 * - State - единое состояние UI (Task3State) - определено в Task3Contracts.kt
 * - Intent - действия пользователя (Task3Intent) - определено в Task3Contracts.kt
 * - ViewModel обрабатывает Intent и обновляет State
 * 
 * Все контракты MVI (State, Intent, UiState) находятся в файле Task3Contracts.kt
 * 
 * Требования:
 * 1. Скопируйте код из Task2Screen.kt (или начните с Task1Screen, если нужно)
 * 2. Подключите ViewModel через: val viewModel: Task3ViewModel = viewModel()
 * 3. Используйте collectAsState() для наблюдения за единым состоянием:
 *    - val state by viewModel.state.collectAsState()
 *    - Доступ к полям: state.name, state.email, state.message, state.uiState, state.validationErrors
 * 4. Создайте форму с тремя TextField (имя, email, сообщение)
 * 5. Отправляйте Intent через viewModel.handleIntent():
 *    - Task3Intent.NameChanged(name) - при изменении имени
 *    - Task3Intent.EmailChanged(email) - при изменении email
 *    - Task3Intent.MessageChanged(message) - при изменении сообщения
 *    - Task3Intent.SubmitClicked - при нажатии "Отправить"
 *    - Task3Intent.RetryClicked - при нажатии "Повторить"
 *    - Task3Intent.ClearClicked - при нажатии "Очистить"
 * 6. Отображайте разные состояния UI:
 *    - UiState.Loading -> показать CircularProgressIndicator
 *    - UiState.Success -> показать результат
 *    - UiState.Error -> показать сообщение об ошибке
 *    - UiState.ValidationError -> показать ошибки валидации для каждого поля
 * 
 * Подсказки:
 * - Используйте TextField с параметрами value и onValueChange
 * - Для ошибок валидации используйте isError и supportingText
 * - Используйте Column для вертикальной компоновки формы
 * - Примените модификаторы: padding, spacing, fillMaxWidth
 * - Для индикатора загрузки используйте CircularProgressIndicator
 * - Отправляйте Intent: viewModel.handleIntent(Task3Intent.NameChanged(newName))
 */
@Composable
fun Task3Screen() {
    // TODO: Реализуйте экран с ViewModel и формой
}
