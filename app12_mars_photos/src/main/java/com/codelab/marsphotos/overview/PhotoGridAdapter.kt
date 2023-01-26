package com.codelab.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codelab.marsphotos.databinding.GridViewItemBinding
import com.codelab.marsphotos.network.MarsPhoto

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class PhotoGridAdapter
	: ListAdapter<MarsPhoto, PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

	/**
	 * The MarsPhotosViewHolder constructor takes the binding variable from the associated
	 * GridViewItem, which nicely gives it access to the full [MarsPhoto] information.
	 */
	class MarsPhotoViewHolder(
		private var binding: GridViewItemBinding
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(marsPhoto: MarsPhoto) {
			binding.photo = marsPhoto
			// This is important, because it forces the data binding to execute immediately,
			// which allows the RecyclerView to make the correct view size measurements
			binding.executePendingBindings()
		}
	}

	/**
	 * Create new [RecyclerView] item views (invoked by the layout manager)
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
		return MarsPhotoViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
	}

	/**
	 * Replaces the contents of a view (invoked by the layout manager)
	 */
	override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
		val marsPhoto = getItem(position)
		holder.bind(marsPhoto)
	}

	/**
	 * Allows the RecyclerView to determine which items have changed when the [List] of
	 * [MarsPhoto] has been updated.
	 */
	companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
		override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
			return oldItem.imgSrcUrl == newItem.imgSrcUrl
		}
	}
}