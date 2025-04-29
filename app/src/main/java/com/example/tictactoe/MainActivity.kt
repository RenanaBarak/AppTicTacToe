package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var statusText: TextView
    private lateinit var resetButton: Button

    private var currentPlayer = "X"
    private var board = Array(9) { "" }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(9) { i ->
            findViewById(resources.getIdentifier("button$i", "id", packageName))
        }


        statusText = findViewById(R.id.statusText)
        resetButton = findViewById(R.id.resetButton)


        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                onButtonClicked(i)
            }
        }


        resetButton.setOnClickListener {
            resetGame()
        }
    }

    fun onButtonClicked(index: Int) {
        if (board[index] != "" || !gameActive) return

        board[index] = currentPlayer
        buttons[index].text = currentPlayer

        if (checkWinner()) {
            gameActive = false
            statusText.text = "Player $currentPlayer wins!"
            resetButton.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusText.text = "Turn: $currentPlayer"
        }
    }

    fun checkWinner(): Boolean {
        val winningCombinations = arrayOf(
            arrayOf(0, 1, 2),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8),
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(0, 4, 8),
            arrayOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (board[a] == board[b] && board[b] == board[c] && board[a] != "") {
                return true
            }
        }
        return false
    }

    fun resetGame() {
        currentPlayer = "X"
        board = Array(9) { "" }
        gameActive = true
        statusText.text = "Turn: $currentPlayer"
        for (button in buttons) {
            button.text = ""
        }
    }
}