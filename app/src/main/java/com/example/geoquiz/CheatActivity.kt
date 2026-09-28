package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val questionText = findViewById<TextView>(R.id.cheatQuestion)
        val answerText = findViewById<TextView>(R.id.answerText)
        val backButton = findViewById<Button>(R.id.backButton)

        val question = intent.getStringExtra("question")
        val answer = intent.getBooleanExtra("answer", false)

        questionText.text = question

        answerText.text = "Correct Answer: ${if (answer) "TRUE" else "FALSE"}"

        backButton.setOnClickListener {
            finish()
        }
    }
}