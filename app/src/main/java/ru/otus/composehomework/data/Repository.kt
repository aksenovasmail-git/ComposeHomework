package ru.otus.composehomework.data

import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Готовый репозиторий для работы с данными.
 * Используется в задании 3.
 */
class Repository {
    
    /**
     * Симуляция загрузки данных с сервера
     */
    suspend fun loadData(query: String): Result<String> {
        delay(1500) // Симуляция сетевой задержки
        
        return if (Random.nextBoolean()) {
            Result.success("Данные успешно загружены для запроса: $query")
        } else {
            Result.failure(Exception("Ошибка сети"))
        }
    }
    
    /**
     * Отправка формы с данными
     */
    suspend fun submitForm(
        name: String,
        email: String,
        message: String
    ): Result<FormResult> {
        delay(2000) // Симуляция сетевой задержки
        
        return if (Random.nextFloat() > 0.3f) { // 70% успех
            Result.success(
                FormResult(
                    id = Random.nextInt(1000, 9999),
                    message = "Форма успешно отправлена!"
                )
            )
        } else {
            Result.failure(Exception("Ошибка отправки данных"))
        }
    }
}

data class FormResult(
    val id: Int,
    val message: String
)
