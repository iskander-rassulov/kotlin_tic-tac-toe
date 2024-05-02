package com.example.tictactoegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private var player1Turn = true
    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        // Инициализируем кнопки
        buttons = Array(3) { r ->
            Array(3) { c ->
                val buttonID = "button_${r}${c}"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).apply {
                    setOnClickListener { onButtonClick(this) }
                }
            }
        }
    }

    fun onButtonClick(button: Button) {
        if (button.text.isNotEmpty()) {
            return
        }

        if (player1Turn) {
            button.text = "X"
        } else {
            button.text = "O"
        }

        roundCount++

        if (checkForWin()) {
            val winner = if (player1Turn) "Player 1 wins!" else "Player 2 wins!"
            updateStatus(winner)
        } else if (roundCount == 9) {
            updateStatus("Draw!")
        } else {
            player1Turn = !player1Turn
            updateStatus(if (player1Turn) "Player 1's turn (X)" else "Player 2's turn (O)")
        }

        findViewById<Button>(R.id.restartButton).visibility = View.VISIBLE
        findViewById<Button>(R.id.homeButton).visibility = View.VISIBLE

    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { r ->
            Array(3) { c -> buttons[r][c].text.toString() }
        }

        // Проверка строк
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] != "") return true
        }

        // Проверка столбцов
        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] != "") return true
        }

        // Проверка диагоналей
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != "") return true
        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != "") return true

        return false
    }



    private fun updateStatus(status: String) {
        val statusTextView = findViewById<TextView>(R.id.gameStatus)
        statusTextView.text = status

        val restartButton = findViewById<Button>(R.id.restartButton)
        val homeButton = findViewById<Button>(R.id.homeButton)

        if (status.contains("wins") || status == "Draw!") {
            restartButton.visibility = View.VISIBLE
            homeButton.visibility = View.VISIBLE
        } else {
            restartButton.visibility = View.GONE
            homeButton.visibility = View.GONE
        }
    }


    fun restartGame(view: View) {
        buttons.forEach { row ->
            row.forEach { button ->
                button.text = ""
            }
        }
        roundCount = 0
        player1Turn = true
        findViewById<TextView>(R.id.gameStatus).text = "Player 1's turn (X)"
        findViewById<Button>(R.id.restartButton).visibility = View.GONE
        findViewById<Button>(R.id.homeButton).visibility = View.GONE
    }

    fun goHome(view: View) {
        finish()
    }


}


