package com.hanyeop.presentation.view

import androidx.activity.addCallback
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.hanyeop.presentation.R
import com.hanyeop.presentation.base.BaseFragmentMain
import com.hanyeop.presentation.utils.KeepStateNavigator
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import com.hanyeop.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragmentMain<FragmentMainBinding>(R.layout.fragment_main) {

    override fun init() {
        initNavigation()
        initBackPressed()
        initClickListener()
    }

    private fun initNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val keepStateNavigator = KeepStateNavigator(
            requireContext(),
            childFragmentManager,
            R.id.fragment_container_view
        )
        navController.navigatorProvider.addNavigator(keepStateNavigator)
        navController.setGraph(R.navigation.nav_main)
        ExpandableBottomBarNavigationUI.setupWithNavController(binding.expandableBottomBar,navController)
    }

    private fun initBackPressed() {
        var lastPressedTime = 0L
        requireActivity().onBackPressedDispatcher.addCallback {
            val currentTime = System.currentTimeMillis()
            if (lastPressedTime + 1500 > currentTime) {
                requireActivity().finish()
            } else {
                lastPressedTime = currentTime
                Toast.makeText(requireContext(), "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initClickListener(){
        binding.apply {
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_musicSearchFragment)
            }
        }
    }
}