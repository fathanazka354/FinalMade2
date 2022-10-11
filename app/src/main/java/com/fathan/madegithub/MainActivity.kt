package com.fathan.madegithub

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import com.fathan.madegithub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(mainBinding.bottomNavView, navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> mainBinding.bottomNavView.visibility = View.GONE
                else -> mainBinding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }

    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        Toast.makeText(this@MainActivity, "Power Connected", Toast.LENGTH_SHORT).show()
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        Toast.makeText(this@MainActivity, "Power Disconnected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}