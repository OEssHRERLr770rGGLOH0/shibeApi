package com.tobianoapps.shibeapi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tobianoapps.shibeapi.databinding.ActivityMainBinding

class ShibeActivity : AppCompatActivity() {

    /*** PROPERTIES ***/
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val shibeViewModel: ShibeViewModel by viewModels()

    /*** LIFECYCLE ***/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init navigation
        val navController = findNavController(R.id.nav_host)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Setup action bar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setup fab click
        binding.fab.setOnClickListener {
            shibeViewModel.onFabClick()
        }

    }

    /**
     * Makes the up arrow work properly
     */
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host).navigateUp()
}