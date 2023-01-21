package com.codelab.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val rollButton: Button = findViewById(R.id.roll_button)

		rollButton.setOnClickListener {
//			Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT).show()
//
//			val resultTextView: TextView = findViewById(R.id.dice_text)
//			resultTextView.text = "6"

			rollDice()
		}
	}

	/**
	 * Roll the dice and update the screen with the result.
	 */
	private fun rollDice() {
		// Create new Dice object with 6 sides and roll it
		val dice = Dice(6)
		val diceRoll = dice.roll()

		// Update the screen with the dice roll
		val resultTextView: TextView = findViewById(R.id.dice_text)
		resultTextView.text = diceRoll.toString()
	}
}