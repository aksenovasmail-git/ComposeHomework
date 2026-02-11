package ru.otus.composehomework.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.otus.composehomework.ui.task1.Task1Screen
import ru.otus.composehomework.ui.task2.Task2Screen
import ru.otus.composehomework.ui.task3.Task3Screen

sealed class Task(val title: String, val screen: @Composable () -> Unit) {
    object Task1 : Task("Задание 1: Переписывание XML на Compose", { Task1Screen() })
    object Task2 : Task("Задание 2: Добавление состояния", { Task2Screen() })
    object Task3 : Task("Задание 3: ViewModel и состояние", { Task3Screen() })
}

@Composable
fun MainScreen() {
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    val currentTask = selectedTask
    if (currentTask == null) {
        TaskListScreen(
            onTaskSelected = { task -> selectedTask = task }
        )
    } else {
        TaskDetailScreen(
            task = currentTask,
            onBack = { selectedTask = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(onTaskSelected: (Task) -> Unit) {
    val tasks = listOf(Task.Task1, Task.Task2, Task.Task3)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Compose Homework",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTaskSelected(task) }
                ) {
                    Text(
                        text = task.title,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(task: Task, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            task.screen()
        }
    }
}
