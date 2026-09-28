package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val text: String,
    val answer: Boolean
)

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var scoreText: TextView

    private lateinit var feedbackText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button

    private lateinit var restartButton: Button

    private val questions = listOf(
        Question("The capital of France is Paris.", true),
        Question("The Pacific Ocean is the largest ocean on Earth.", true),
        Question("Australia is located in Europe.", false),
        Question("The Nile River is in Africa.", true),
        Question("Brazil is located in South America.", true),
        Question("Mount Everest is located in Canada.", false)
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionText = findViewById(R.id.questionText)
        scoreText = findViewById(R.id.scoreText)
        feedbackText = findViewById(R.id.feedbackText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        cheatButton = findViewById(R.id.cheatButton)
        restartButton = findViewById(R.id.restartButton)

        if (savedInstanceState != null) {
            currentQuestionIndex =
                savedInstanceState.getInt("currentQuestionIndex", 0)

            score =
                savedInstanceState.getInt("score", 0)

            answered =
                savedInstanceState.getBoolean("answered", false)
        }

        displayQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            nextQuestion()
        }

        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)

            intent.putExtra(
                "question",
                questions[currentQuestionIndex].text
            )

            intent.putExtra(
                "answer",
                questions[currentQuestionIndex].answer
            )

            startActivity(intent)
        }

        restartButton.setOnClickListener {
            currentQuestionIndex = 0
            score = 0
            answered = false
            feedbackText.text = ""

            nextButton.isEnabled = true

            displayQuestion()
        }
    }




    private fun displayQuestion() {
        questionText.text = questions[currentQuestionIndex].text
        scoreText.text = "Score: $score / ${questions.size}"

        trueButton.isEnabled = !answered
        falseButton.isEnabled = !answered

        if (currentQuestionIndex == questions.size - 1) {
            nextButton.text = "FINISH"
        } else {
            nextButton.text = "NEXT"
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {

        if (answered) {
            return
        }

        val correctAnswer = questions[currentQuestionIndex].answer

        if (userAnswer == correctAnswer) {
            score++

            feedbackText.text = "CORRECT!"
            feedbackText.setTextColor(
                android.graphics.Color.rgb(0, 150, 0)
            )

            Toast.makeText(
                this,
                "Correct!",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            feedbackText.text = "INCORRECT!"
            feedbackText.setTextColor(
                android.graphics.Color.rgb(200, 0, 0)
            )

            Toast.makeText(
                this,
                "Incorrect!",
                Toast.LENGTH_SHORT
            ).show()
        }

        answered = true
        scoreText.text = "Score: $score / ${questions.size}"

        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun nextQuestion() {

        if (!answered) {
            Toast.makeText(
                this,
                "Please answer the question first.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            answered = false
            feedbackText.text = ""
            displayQuestion()
        } else {
            questionText.text = "Quiz Complete!"
            scoreText.text = "Final Score: $score / ${questions.size}"

            trueButton.isEnabled = false
            falseButton.isEnabled = false
            nextButton.isEnabled = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            "currentQuestionIndex",
            currentQuestionIndex
        )

        outState.putInt(
            "score",
            score
        )

        outState.putBoolean(
            "answered",
            answered
        )
    }
}