package com.rakarguntara.filmku.view.home.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rakarguntara.filmku.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    private var _binding : FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
    }

    private fun setupAnimation() {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1F).setDuration(300),
                ObjectAnimator.ofFloat(binding.tvBy, View.ALPHA, 1F).setDuration(500),
                ObjectAnimator.ofFloat(binding.tvCreatorName, View.ALPHA, 1F).setDuration(500),
                ObjectAnimator.ofFloat(binding.tvOverview, View.ALPHA, 1F).setDuration(700),
                ObjectAnimator.ofFloat(binding.tvOverviewActual, View.ALPHA, 1F).setDuration(800),
                ObjectAnimator.ofFloat(binding.tvCredits, View.ALPHA, 1F).setDuration(900),
                ObjectAnimator.ofFloat(binding.tvTmdb, View.ALPHA, 1F).setDuration(100),
                ObjectAnimator.ofFloat(binding.tvTmdbActual, View.ALPHA, 1F).setDuration(1100),
                ObjectAnimator.ofFloat(binding.tvIconify, View.ALPHA, 1F).setDuration(1200),
                ObjectAnimator.ofFloat(binding.tvIconifyActual, View.ALPHA, 1F).setDuration(1300),
                ObjectAnimator.ofFloat(binding.tvFreepik, View.ALPHA, 1F).setDuration(1400),
                ObjectAnimator.ofFloat(binding.tvFreepikActual, View.ALPHA, 1F).setDuration(1500),
                ObjectAnimator.ofFloat(binding.ivThankyou, View.ALPHA, 1F).setDuration(1600),

            )
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}