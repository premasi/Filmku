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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setup bottom navigation
        setupBottomNavigation()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_fragment, fragment)
        fragmentTransaction.commit()
    }

    private fun setupBottomNavigation() {
        CoroutineScope(Dispatchers.Main).launch {
            replaceFragment(HomeFragment())
            binding.bnvMenu.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.bn_home -> replaceFragment(HomeFragment())
                    R.id.bn_tag_favorite -> replaceFragment(TagFavoritesFragment())
                    R.id.bn_about -> replaceFragment(AboutFragment())
                    else -> {}
                }
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}