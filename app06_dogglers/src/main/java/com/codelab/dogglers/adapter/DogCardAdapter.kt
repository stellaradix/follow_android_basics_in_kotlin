package com.codelab.dogglers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codelab.dogglers.R
import com.codelab.dogglers.const.Layout
import com.codelab.dogglers.data.DataSource

/**
 * Adapter to inflate the appropriate list item layout and populate the view with information
 * from the appropriate data source
 */
class DogCardAdapter(
	private val context: Context?,
	private val layout: Int
) : RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

	// Initialize the data using the List found in data/DataSource
	private val data = DataSource.dogs

	/**
	 * Initialize view elements
	 */
	class DogCardViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
		// Declare and initialize all of the list item UI components
		val image: ImageView = view!!.findViewById(R.id.item_image)
		val name: TextView = view!!.findViewById(R.id.item_name)
		val age: TextView = view!!.findViewById(R.id.item_age)
		val hobbies: TextView = view!!.findViewById(R.id.item_hobbies)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
		// Use a conditional to determine the layout type and set it accordingly.
		// if the layout variable is Layout.GRID the grid list item should be used.
		// Otherwise the the vertical/horizontal list item should be used.
		val layoutResourceId: Int = when (layout) {
			Layout.GRID -> R.layout.grid_list_item
			else -> R.layout.vertical_horizontal_list_item
		}

		// Inflate the layout
		val adapterLayout = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)

		// Null should not be passed into the view holder.
		// This should be updated to reflect the inflated layout.
		return DogCardViewHolder(adapterLayout)
	}

	override fun getItemCount(): Int = data.size // return the size of the data set instead of 0

	override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {
		// Get the data at the current position
		val dog = data[position]

		// Set the image resource for the current dog
		holder.image.setImageResource(dog.imageResourceId)

		// Set the text for the current dog's name
		holder.name.text = dog.name

		// Set the text for the current dog's age
		holder.age.text = dog.age

		val resources = context?.resources
		// Set the text for the current dog's hobbies
		// by passing the hobbies to the R.string.dog_hobbies string constant.
		// Passing an argument to the string resource looks like:
		// resources?.getString(R.string.dog_hobbies, dog.hobbies)

		// holder.hobbies.text = resources?.getString(R.string.dog_hobbies, dog.hobbies)

		// For Test
		holder.hobbies.text = dog.hobbies
	}
}