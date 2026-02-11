# Задание 3: ViewModel и комплексное состояние (MVI паттерн)

## Цель
Изучить интеграцию ViewModel с Compose и работу с комплексным состоянием используя MVI паттерн.

## Что такое MVI?

MVI (Model-View-Intent) - архитектурный паттерн, где:
- **Model (State)** - единое состояние UI (data class со всеми полями)
- **View** - Compose UI, который отображает State и отправляет Intent
- **Intent** - действия пользователя (sealed class)

## Структура файлов

Все контракты MVI (State, Intent, UiState) определены в файле **Task3Contracts.kt**.
Это хорошая практика - выносить контракты в отдельный файл для лучшей организации кода.

## Задача
Подключить готовый ViewModel к Compose UI и создать форму с валидацией используя MVI паттерн.

## Важно
Вся бизнес-логика во `Task3ViewModel` уже реализована! Вам нужно только подключить её к Compose UI.

## Шаги выполнения

1. Откройте файл `Task3Screen.kt`

2. Подключите ViewModel

3. Подпишитесь на единое состояние

4. Доступ к полям состояния:
   ```kotlin
   val name = state.name
   val email = state.email
   val message = state.message
   val uiState = state.uiState
   val validationErrors = state.validationErrors
   ```

5. Создайте форму с тремя TextField и отправляйте Intent:
   ```kotlin
   TextField(
       value = state.name,
       onValueChange = { 
           viewModel.handleIntent(Task3Intent.NameChanged(it)) 
       },
       label = { Text("Имя") },
   )
   ```

6. Обработайте ошибки валидации:
   ```kotlin
   val nameError = state.validationErrors?.errors?.get(FieldType.NAME)
   TextField(
       value = state.name,
       onValueChange = { 
           viewModel.handleIntent(Task3Intent.NameChanged(it)) 
       },
       label = { Text("Имя") },
       isError = nameError != null,
       supportingText = nameError?.let { { Text(it) } }
   )
   ```

7. Отображайте разные состояния UI:
   ```kotlin
   when (state.uiState) {
       is UiState.Loading -> {
           CircularProgressIndicator()
       }
       is UiState.Success -> {
           Text("Успех: ${state.uiState.result.message}")
       }
       is UiState.Error -> {
           Text("Ошибка: ${state.uiState.message}")
           Button(
               onClick = { 
                   viewModel.handleIntent(Task3Intent.RetryClicked) 
               }
           ) {
               Text("Повторить")
           }
       }
       is UiState.ValidationError -> {
           // Ошибки валидации уже обработаны в TextField
       }
       is UiState.Idle -> {
           // Форма готова к заполнению
       }
   }
   ```

8. Подключите кнопки:
   ```kotlin
   Button(
       onClick = { 
           viewModel.handleIntent(Task3Intent.SubmitClicked) 
       },
       enabled = state.uiState !is UiState.Loading
   ) {
       Text("Отправить")
   }
   
   Button(
       onClick = { 
           viewModel.handleIntent(Task3Intent.ClearClicked) 
       }
   ) {
       Text("Очистить")
   }
   ```

## Ожидаемый результат

- Форма с тремя полями (имя, email, сообщение)
- Валидация полей с отображением ошибок
- Индикатор загрузки во время отправки
- Отображение результата или ошибки
- Возможность повторить запрос при ошибке

### Скриншоты

Форма с полями ввода:
![Задание 3 - Форма](../../../../../../readme/task3_1.png)

Успешная отправка формы:
![Задание 3 - Успех](../../../../../../readme/task3_success.png)

Ошибка валидации:
![Задание 3 - Ошибка валидации](../../../../../../readme/task3_fail.png)

## Доступные Intent

- `Task3Intent.NameChanged(name: String)` - изменение имени
- `Task3Intent.EmailChanged(email: String)` - изменение email
- `Task3Intent.MessageChanged(message: String)` - изменение сообщения
- `Task3Intent.SubmitClicked` - отправка формы
- `Task3Intent.RetryClicked` - повтор при ошибке
- `Task3Intent.ClearClicked` - очистка формы

## Структура State

Все контракты определены в `Task3Contracts.kt`:

```kotlin
data class Task3State(
    val name: String = "",
    val email: String = "",
    val message: String = "",
    val uiState: UiState = UiState.Idle,
    val validationErrors: ValidationErrors? = null
)
```

Для использования в Compose просто импортируйте из пакета `ru.otus.composehomework.ui.task3`.

## Состояния UiState

- `UiState.Idle` - начальное состояние
- `UiState.Loading` - загрузка данных
- `UiState.Success(result)` - успешная отправка
- `UiState.Error(message)` - ошибка сети
- `UiState.ValidationError(errors)` - ошибки валидации

## Преимущества MVI

- Единое состояние - все данные в одном месте
- Предсказуемость - состояние изменяется только через Intent
- Легче тестировать - можно тестировать обработку Intent отдельно
- Четкое разделение ответственности
