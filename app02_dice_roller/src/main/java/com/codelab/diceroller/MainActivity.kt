package com.codelab.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

		// Determine which drawable resource ID to use based on the dice roll
		val diceImageResId = when (diceRoll) {
			1 -> R.drawable.dice_1
			2 -> R.drawable.dice_2
			3 -> R.drawable.dice_3
			4 -> R.drawable.dice_4
			5 -> R.drawable.dice_5
			else -> R.drawable.dice_6
		}

		// Find the ImageView in the layout
		val diceImage: ImageView = findViewById(R.id.dice_image)

		// Update the ImageView with the correct drawable resource ID
		diceImage.setImageResource(diceImageResId)

		// Update the content description
		diceImage.contentDescription = diceRoll.toString()
	}
}