package com.codelab.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.codelab.cupcake.databinding.FragmentPickupBinding
import com.codelab.cupcake.model.OrderViewModel

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {

	// Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
	private val sharedViewModel: OrderViewModel by activityViewModels()

	// Binding object instance corresponding to the fragment_pickup.xml layout
	// This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
	// when the view hierarchy is attached to the fragment.
	private var binding: FragmentPickupBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
		binding = fragmentBinding
		return fragmentBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding?.apply {
			lifecycleOwner = viewLifecycleOwner
			viewModel = sharedViewModel
			pickupFragment = this@PickupFragment
		}
	}

	/**
	 * Navigate to the next screen to see the order summary.
	 */
	fun goToNextScreen() {
		findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
	}

	/**
	 * This fragment lifecycle method is called when the view hierarchy associated with the fragment
	 * is being removed. As a result, clear out the binding object.
	 */
	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	/**
	 * Cancel the order and start over.
	 */
	fun cancelOrder() {
		// Reset order in view model
		sharedViewModel.resetOrder()

		// Navigate back to the [StartFragment] to start over
		findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
	}
}