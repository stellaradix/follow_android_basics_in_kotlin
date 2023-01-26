package com.codelab.lunchtray

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.codelab.lunchtray.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	// Binding object instance corresponding to the activity_main.xml layout
	// when the view hierarchy is attached to the fragment.
	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Retrieve NavController from the NavHostFragment
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

		navController = navHostFragment.navController

		// Set up the action bar for use with the NavController
		setupActionBarWithNavController(navController)
	}

	/**
	 * Handle navigation when the user chooses Up from the action bar.
	 */
	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}