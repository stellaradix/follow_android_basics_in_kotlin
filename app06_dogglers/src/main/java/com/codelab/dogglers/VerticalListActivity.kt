package com.codelab.dogglers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codelab.dogglers.adapter.DogCardAdapter
import com.codelab.dogglers.const.Layout
import com.codelab.dogglers.databinding.ActivityVerticalListBinding

class VerticalListActivity : AppCompatActivity() {

	private lateinit var binding: ActivityVerticalListBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityVerticalListBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.verticalRecyclerView.adapter = DogCardAdapter(
			applicationContext,
			Layout.VERTICAL
		)

		// Specify fixed size to improve performance
		binding.verticalRecyclerView.setHasFixedSize(true)

		// Enable up button for backward navigation
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}