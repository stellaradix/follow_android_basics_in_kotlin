package com.codelab.diceroller

import org.junit.Assert
import org.junit.Test

class DiceTest {

	val numSides = 6

	val myDice = Dice(numSides)

	@Test
	fun generates_number() {
		Assert.assertTrue(
			"The value of dice number was not between 1 and ${numSides}",
			myDice.roll() in 1..numSides
		)
	}
}