package com.codelab.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codelab.busschedule.databinding.StopScheduleFragmentBinding
import com.codelab.busschedule.viewmodels.BusScheduleViewModel
import com.codelab.busschedule.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class StopScheduleFragment : Fragment() {

	companion object {
		var STOP_NAME = "stopName"
	}

	private val viewModel: BusScheduleViewModel by activityViewModels {
		BusScheduleViewModelFactory(
			(activity?.application as BusScheduleApplication).database.scheduleDao()
		)
	}

	private var _binding: StopScheduleFragmentBinding? = null

	private val binding get() = _binding!!

	private lateinit var recyclerView: RecyclerView

	private lateinit var stopName: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		arguments?.let {
			stopName = it.getString(STOP_NAME).toString()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = StopScheduleFragmentBinding.inflate(inflater, container, false)
		val view = binding.root
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		recyclerView = binding.recyclerView
		recyclerView.layoutManager = LinearLayoutManager(requireContext())

		val busStopAdapter = BusStopAdapter({})
		recyclerView.adapter = busStopAdapter

		// submitList() is a call that accesses the database. To prevent the
		// call from potentially locking the UI, you should use a
		// coroutine scope to launch the function. Using GlobalScope is not
		// best practice, and in the next step we'll see how to improve this.
		lifecycle.coroutineScope.launch {
			viewModel.scheduleForStopName(stopName).collect() {
				busStopAdapter.submitList(it)
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}