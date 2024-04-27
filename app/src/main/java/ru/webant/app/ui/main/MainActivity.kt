package ru.webant.app.ui.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.viewModel
import ru.webant.app.R
import ru.webant.app.databinding.ActivityMainBinding
import ru.webant.app.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModel()

    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.container
        ) as NavHostFragment
        views.bottomNavigationView.setupWithNavController(navHostFragment.navController)
        setupActionBarWithNavController(navHostFragment.navController)
    }
}