import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoegame.R

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
            if (player1Turn) {
                updateStatus("Player 1 wins!")
            } else {
                updateStatus("Player 2 wins!")
            }
        } else if (roundCount == 9) {
            updateStatus("Draw!")
        } else {
            player1Turn = !player1Turn
            updateStatus(if (player1Turn) "Player 1's turn (X)" else "Player 2's turn (O)")
        }
    }

    private fun checkForWin(): Boolean {
        // Логика проверки победы
        // Используйте массивы buttons для проверки состояния поля
        return false // Возвращаемый результат должен основываться на проверке
    }

    private fun updateStatus(status: String) {
        findViewById<TextView>(R.id.gameStatus).text = status
    }
}


