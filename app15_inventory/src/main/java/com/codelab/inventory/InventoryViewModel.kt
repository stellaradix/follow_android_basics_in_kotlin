package com.codelab.inventory

import androidx.lifecycle.*
import com.codelab.inventory.data.Item
import com.codelab.inventory.data.ItemDao
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 */
class InventoryViewModel(
	private val itemDao: ItemDao
) : ViewModel() {

	// Cache all items form the database using LiveData.
	val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

	/**
	 * Launching a new coroutine to update an item in a non-blocking way
	 */
	private fun updateItem(item: Item) {
		viewModelScope.launch {
			itemDao.update(item)
		}
	}

	fun deleteItem(item: Item) {
		viewModelScope.launch {
			itemDao.delete(item)
		}
	}

	/**
	 * Retrieve an item from the repository.
	 */
	fun retrieveItem(id: Int): LiveData<Item> {
		return itemDao.getItem(id).asLiveData()
	}

	/**
	 * Returns true if stock is available to sell, false otherwise.
	 */
	fun isStockAvailable(item: Item): Boolean {
		return item.quantityInStock > 0
	}

	/**
	 * Decreases the stock by one unit and updates the database.
	 */
	fun sellItem(item: Item) {
		if (item.quantityInStock > 0) {
			val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
			updateItem(newItem)
		}
	}

	/**
	 * Called to update an existing entry in the Inventory database.
	 * Returns an instance of the [Item] entity class with the item info updated by the user.
	 */
	private fun getUpdatedItemEntry(
		itemId: Int,
		itemName: String,
		itemPrice: String,
		itemCount: String
	): Item {
		return Item(
			id = itemId,
			itemName = itemName,
			itemPrice = itemPrice.toDouble(),
			quantityInStock = itemCount.toInt()
		)
	}

	/**
	 * Updates an existing Item in the database.
	 */
	fun updateItem(
		itemId: Int,
		itemName: String,
		itemPrice: String,
		itemCount: String
	) {
		val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
		updateItem(updatedItem)
	}

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