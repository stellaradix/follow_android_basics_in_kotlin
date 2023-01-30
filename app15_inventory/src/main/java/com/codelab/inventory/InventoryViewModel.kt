package com.codelab.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.codelab.inventory.data.Item
import com.codelab.inventory.data.ItemDao
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 */
class InventoryViewModel(
	private val itemDao: ItemDao
) : ViewModel() {

	/**
	 * Launching a new coroutine to insert an item in a non-blocking way
	 */
	private fun insertItem(item: Item) {
		viewModelScope.launch {
			itemDao.insert(item)
		}
	}

	/**
	 * Returns an instance of the [Item] entity class with the item info entered by the user.
	 * This will be used to add a new entry to the Inventory database.
	 */
	private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
		return Item(
			itemName = itemName,
			itemPrice = itemPrice.toDouble(),
			quantityInStock = itemCount.toInt()
		)
	}

	/**
	 * Inserts the new Item into database.
	 */
	fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
		val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
		insertItem(newItem)
	}
	
	/**
	 * Returns an instance of the [Item] entity class with the item info entered by the user.
	 * This will be used to add a new entry to the Inventory database.
	 */
	fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
		if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
			return false
		}
		return true
	}
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class InventroyViewModelFactory(
	private val itemDao: ItemDao
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
			return InventoryViewModel(itemDao) as T
		}
		throw java.lang.IllegalArgumentException("Unknown ViewModel class")
	}
}