package com.rakarguntara.filmku.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rakarguntara.filmku.databinding.ActivityMainBinding
import com.rakarguntara.filmku.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setup animation logo
        setupAnimation()

        //Go to home page
        goToHomePage()
    }

    private fun goToHomePage() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity as Activity
            ).toBundle())
            delay(2001)
            finish()
        }
    }

    private fun setupAnimation() {
        AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(binding.ivAppLogo, View.ALPHA, 1F).setDuration(1500)
            )
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}