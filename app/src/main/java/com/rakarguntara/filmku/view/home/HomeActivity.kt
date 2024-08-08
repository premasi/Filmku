package com.rakarguntara.filmku.view.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.ActivityHomeBinding
import com.rakarguntara.filmku.view.home.fragments.AboutFragment
import com.rakarguntara.filmku.view.home.fragments.HomeFragment
import com.rakarguntara.filmku.view.home.fragments.TagFavoritesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        //setup bottom navigation
        setupBottomNavigation()

        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_fragment, HomeFragment())
                .commitNow()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment, fragment)
            .commit()
    }

    private fun setupBottomNavigation() {
        binding.bnvMenu.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.bn_home -> HomeFragment()
                R.id.bn_tag_favorite -> TagFavoritesFragment()
                R.id.bn_about -> AboutFragment()
                else -> return@setOnItemSelectedListener false
            }
            replaceFragment(fragment) // Perform fragment replacement
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}