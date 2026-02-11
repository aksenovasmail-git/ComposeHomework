# Задание 1: Переписывание XML на Compose

## Цель
Изучить базовые компоненты Compose, переписав XML layout на Compose.

## Задача
Переписать экран из XML на Compose, создав функцию `@Composable Task1Screen()`.

## Начальный код
- `Task1Fragment.kt` - Fragment с XML layout
- `fragment_task1.xml` - XML layout с простым экраном

## Требования

1. Создайте функцию `@Composable Task1Screen()`
2. Используйте компоненты: `Text`, `Button`, `Image` или `Icon`
3. Используйте `Column` для вертикальной компоновки
4. Примените модификаторы: `padding`, `spacing`
5. Реализуйте ту же функциональность: при нажатии кнопки изменять текст

## Шаги выполнения

1. Откройте файл `Task1Screen.kt`

2. Создайте функцию:
   ```kotlin
   @Composable
   fun Task1Screen() {
       // Ваш код здесь
   }
   ```

3. Используйте `Column` для вертикальной компоновки:
   ```kotlin
   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(16.dp),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(32.dp)
   ) {
       // Элементы UI
   }
   ```

4. Добавьте Text для отображения текста:
   ```kotlin
   var welcomeText by remember { mutableStateOf("Добро пожаловать!") }
   
   Text(text = welcomeText)
   ```

5. Добавьте Image или Icon:
   ```kotlin
   Image(
       painter = painterResource(id = android.R.drawable.ic_dialog_info),
       contentDescription = "Welcome",
       modifier = Modifier.size(120.dp)
   )
   ```

6. Добавьте Button с обработчиком:
   ```kotlin
   Button(
       onClick = { welcomeText = "Кнопка нажата!" }
   ) {
       Text("Нажми меня")
   }
   ```

## Ожидаемый результат

![Задание 1 - Результат](../../../../../../readme/task1.png)

- Экран выглядит идентично XML версии
- При нажатии на кнопку текст изменяется
- Используются базовые компоненты Compose

## Подсказки

- Используйте `remember { mutableStateOf() }` для хранения состояния текста
- Для изображения используйте `Image` с `painterResource` или `Icon`
- Примените модификатор `.padding(16.dp)` к `Column`
- Используйте `Arrangement.spacedBy()` для отступов между элементами
- Не забудьте импортировать необходимые компоненты
