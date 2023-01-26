package com.codelab.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codelab.lunchtray.data.DataSource
import java.text.NumberFormat
import java.util.*

class OrderViewModel : ViewModel() {

	// Map of menu items
	val menuItems = DataSource.menuItems

	// Default values for item prices
	private var previousEntreePrice = 0.0
	private var previousSidePrice = 0.0
	private var previousAccompanimentPrice = 0.0

	// Default tax rate
	private val taxRate = 0.08

	// Entree for the order
	private val _entree = MutableLiveData<MenuItem?>()
	val entree: LiveData<MenuItem?> = _entree

	// Side for the order
	private val _side = MutableLiveData<MenuItem?>()
	val side: LiveData<MenuItem?> = _side

	// Accompaniment for the order.
	private val _accompaniment = MutableLiveData<MenuItem?>()
	val accompaniment: LiveData<MenuItem?> = _accompaniment

	// Subtotal for the order
	private val _subtotal = MutableLiveData(0.0)
	val subtotal: LiveData<String> = Transformations.map(_subtotal) {
		NumberFormat.getCurrencyInstance(Locale.US).format(it)
	}

	// Total cost of the order
	private val _total = MutableLiveData(0.0)
	val total: LiveData<String> = Transformations.map(_total) {
		NumberFormat.getCurrencyInstance(Locale.US).format(it)
	}

	// Tax for the order
	private val _tax = MutableLiveData(0.0)
	val tax: LiveData<String> = Transformations.map(_tax) {
		NumberFormat.getCurrencyInstance(Locale.US).format(it)
	}

	/**
	 * Set the entree for the order.
	 */
	fun setEntree(entree: String) {
		// if _entree.value is not null, set the previous entree price to the current entree price.
		previousEntreePrice = _entree.value?.price ?: 0.0

		// if _subtotal.value is not null subtract the previous entree price from the current subtotal value.
		// This ensures that we only charge for the currently selected entree.
		_subtotal.value = _subtotal.value?.minus(previousEntreePrice)

		// set the current entree value to the menu item corresponding to the passed in string
		_entree.value = menuItems[entree]

		// update the subtotal to reflect the price of the selected entree.
		updateSubtotal(_entree.value?.price ?: 0.0)
	}

	/**
	 * Set the side for the order.
	 */
	fun setSide(side: String) {
		// if _side.value is not null, set the previous side price to the current side price.
		previousSidePrice = _side.value?.price ?: 0.0

		// if _subtotal.value is not null subtract the previous side price from the current subtotal value.
		// This ensures that we only charge for the currently selected side.
		_subtotal.value = _subtotal.value?.minus(previousSidePrice)

		// set the current side value to the menu item corresponding to the passed in string
		_side.value = menuItems[side]

		// update the subtotal to reflect the price of the selected side.
		updateSubtotal(_side.value?.price ?: 0.0)
	}

	/**
	 * Set the accompaniment for the order.
	 */
	fun setAccompaniment(accompaniment: String) {
		// if _accompaniment.value is not null, set the previous accompaniment price to the current accompaniment price.
		previousAccompanimentPrice = _accompaniment.value?.price ?: 0.0

		// if _accompaniment.value is not null subtract the previous accompaniment price from the current subtotal value.
		// This ensures that we only charge for the currently selected accompaniment.
		_subtotal.value = _subtotal.value?.minus(previousAccompanimentPrice)

		// set the current accompaniment value to the menu item corresponding to the passed in string
		_accompaniment.value = menuItems[accompaniment]

		// update the subtotal to reflect the price of the selected accompaniment.
		updateSubtotal(_accompaniment.value?.price ?: 0.0)
	}

	/**
	 * Update subtotal value.
	 */
	private fun updateSubtotal(itemPrice: Double) {
		// if _subtotal.value is not null, update it to reflect the price of the recently added item.
		// Otherwise, set _subtotal.value to equal the price of the item.
		_subtotal.value = (_subtotal.value ?: 0.0).plus(itemPrice)

		// calculate the tax and resulting total
		calculateTaxAndTotal()
	}

	/**
	 * Calculate tax and update total.
	 */
	fun calculateTaxAndTotal() {
		// set _tax.value based on the subtotal and the tax rate.
		_tax.value = (_subtotal.value ?: 0.0).times(taxRate)

		// set the total based on the subtotal and _tax.value.
		_total.value = (_subtotal.value ?: 0.0).plus(_tax.value ?: 0.0)
	}

	/**
	 * Reset all values pertaining to the order.
	 */
	fun resetOrder() {
		// Reset all values associated with an order
		_entree.value = null
		_side.value = null
		_accompaniment.value = null

		_subtotal.value = 0.0
	}

	init {
		resetOrder()
	}
}