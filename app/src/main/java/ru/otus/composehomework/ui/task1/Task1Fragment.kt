package ru.otus.composehomework.ui.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.otus.composehomework.R

/**
 * Начальный Fragment с XML layout.
 * В задании 1 нужно переписать этот экран на Compose.
 */
class Task1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val welcomeText = view.findViewById<TextView>(R.id.welcomeText)
        val clickButton = view.findViewById<Button>(R.id.clickButton)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        clickButton.setOnClickListener {
            welcomeText.text = "Кнопка нажата!"
        }
    }
}
