package uz.gita.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.quizapp.databinding.ActivityMainBinding
import uz.gita.quizapp.util.makeVisibleOrGone

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        binding.bottomNav.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment, R.id.historyFragment, R.id.addTestFragment -> {
                    makeVisibleOrGone(binding.bottomNav, true)
                }
                else -> makeVisibleOrGone(binding.bottomNav, false)
            }
        }

    }
}