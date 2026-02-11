# Задание 2: Добавление состояния

## Цель
Изучить управление состоянием в Compose используя `remember` и `mutableStateOf`.

## Задача
Развить `Task1Screen`, добавив счетчик кликов.

## Шаги выполнения

1. Откройте файл `Task2Screen.kt`

2. Скопируйте код из `Task1Screen.kt` (или начните с базового экрана)

3. Добавьте состояние счетчика:
   ```kotlin
   var count by remember { mutableStateOf(0) }
   ```
   
   Не забудьте импортировать:
   ```kotlin
   import androidx.compose.runtime.getValue
   import androidx.compose.runtime.setValue
   import androidx.compose.runtime.mutableStateOf
   import androidx.compose.runtime.remember
   ```

4. Измените обработчик кнопки, чтобы увеличивать счетчик:
   ```kotlin
   Button(onClick = { count++ }) {
       Text("Нажми меня")
   }
   ```

5. Добавьте Text для отображения счетчика:
   ```kotlin
   Text(text = "Счетчик: $count")
   ```

## Ожидаемый результат

- При нажатии на кнопку счетчик увеличивается
- Текст с счетчиком обновляется автоматически
- Используется `remember { mutableStateOf() }` для хранения состояния

![Задание 2 - Результат](../../../../../../readme/task2.png)

## Подсказки

- Используйте делегат `by` для удобной работы с состоянием
- Состояние должно быть объявлено внутри функции `@Composable`
- Compose автоматически перерисует UI при изменении состояния
