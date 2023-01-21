package com.codelab.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

	// Create two Dice object with 6 sides
	private val firstDice = Dice(6)
	private val secondDice = Dice(6)

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
		// Roll two dice and get value
		val firstDiceRecord = firstDice.roll()
		val secondDiceRecord = secondDice.roll()

		// Find the View in the layout
		val firstDiceImage: ImageView = findViewById(R.id.dice_1)
		val secondDiceImage: ImageView = findViewById(R.id.dice_2)
		val sumOfDiceRecord: TextView = findViewById(R.id.dice_record_sum)

		// Update the firstDice ImageView
		firstDiceImage.setImageResource(convertRecordToResource(firstDiceRecord))
		firstDiceImage.contentDescription = firstDiceRecord.toString()

		// Update the secondDice ImageView
		secondDiceImage.setImageResource(convertRecordToResource(secondDiceRecord))
		secondDiceImage.contentDescription = secondDiceRecord.toString()

		// Update sum of Dice record
		sumOfDiceRecord.text = "${firstDiceRecord + secondDiceRecord}"
	}

	/**
	 * Determine which drawable resource ID to use based on the dice roll
	 */
	private fun convertRecordToResource(diceRecord: Int): Int {
		return when (diceRecord) {
			1 -> R.drawable.dice_1
			2 -> R.drawable.dice_2
			3 -> R.drawable.dice_3
			4 -> R.drawable.dice_4
			5 -> R.drawable.dice_5
			else -> R.drawable.dice_6
		}
	}
}