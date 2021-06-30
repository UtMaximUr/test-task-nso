package com.nso.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nso.test.R
import com.nso.test.databinding.ActivityMainBinding
import com.nso.test.utils.hide
import com.nso.test.utils.show
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            navController = (supportFragmentManager
                .findFragmentById(nav_host_fragment_container.id) as NavHostFragment)
                .navController
            menu.apply {
                setupWithNavController(navController)
                itemIconTintList = null
                initView()
            }
        }

        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.stockFragment -> {
                        binding.apply {
                            menu.hide()
                        }
                    }
                    else -> {
                        binding.apply {
                            menu.show()
                        }
                    }
                }
            }
    }

    private fun initView() {
        binding.menu.setOnNavigationItemReselectedListener { }
        binding.menu.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.common_stock_list_fragment -> {
                    navController.navigate(R.id.commonFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite_stock_list_fragment -> {
                    navController.navigate(R.id.favoriteFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}