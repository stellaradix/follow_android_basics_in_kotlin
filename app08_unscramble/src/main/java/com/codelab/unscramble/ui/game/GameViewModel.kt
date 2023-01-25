package com.codelab.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

	private var _score = 0
	val score get() = _score

	private var _currentWordCount = 0
	val currentWordCount get() = _currentWordCount

	private lateinit var _currentScrambledWord: String
	val currentScrambledWord get() = _currentScrambledWord

	private var wordsList: MutableList<String> = mutableListOf()
	private lateinit var currentWord: String

	init {
		Log.d("GameFragment", "GameViewModel created!")
		getNextWord()
	}

	override fun onCleared() {
		super.onCleared()
		Log.d("GameFragment", "GameViewModel destroyed!")
	}

	/*
    * Updates currentWord and currentScrambledWord with the next word.
    */
	private fun getNextWord() {
		currentWord = allWordsList.random()

		val tempWord = currentWord.toCharArray()
		tempWord.shuffle()

		while (String(tempWord).equals(currentWord, false)) {
			tempWord.shuffle()
		}

		if (wordsList.contains(currentWord)) {
			getNextWord()
		} else {
			_currentScrambledWord = String(tempWord)
			++_currentWordCount
			wordsList.add(currentWord)
		}
	}

	/*
	* Re-initializes the game data to restart the game.
	*/
	fun reinitializeData() {
		_score = 0
		_currentWordCount = 0
		wordsList.clear()
		getNextWord()
	}

	private fun increaseScore() {
		_score += SCORE_INCREASE
	}

	fun isUserWordCorrect(playerWord: String): Boolean {
		if (playerWord.equals(currentWord, true)) {
			increaseScore()
			return true
		}
		return false
	}

	/*
	* Returns true if the current word count is less than MAX_NO_OF_WORDS.
	* Updates the next word.
	*/
	fun nextWord(): Boolean {
		return if (currentWordCount < MAX_NO_OF_WORDS) {
			getNextWord()
			true
		} else false
	}
}