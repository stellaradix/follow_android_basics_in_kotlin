package com.codelab.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codelab.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.calculateButton.setOnClickListener { calculateTip() }
	}

	private fun calculateTip() {
		val stringInTextField = binding.costOfService.text.toString()
		val cost = stringInTextField.toDoubleOrNull()

		// If the cost is null or 0, then display 0 tip and exit this function early.
		if (cost == null || cost == 0.0) {
			displayTip(0.0)
			return
		}

		val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
			R.id.option_twenty_percent -> 0.20
			R.id.option_eighteen_percent -> 0.18
			else -> 0.15
		}

		var tip = tipPercent * cost
		if (binding.roundUpSwitch.isChecked) tip = kotlin.math.ceil(tip)

		// Display the formatted tip value on screen
		displayTip(tip)
	}

	private fun displayTip(tip: Double) {
		val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
		binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
	}
}