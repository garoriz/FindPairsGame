package com.garif.gamefindpairs.feature.menuview.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garif.gamefindpairs.R
import com.garif.gamefindpairs.databinding.FragmentMenuViewBinding


class MenuViewFragment : Fragment(R.layout.fragment_menu_view) {
    private lateinit var binding: FragmentMenuViewBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(getString(R.string.shared_preferences_name), 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuViewBinding.bind(view)
        val coins = sharedPreferences.getInt("coins", 0)

        with(binding) {
            btnPlay.setOnClickListener {
                view.findNavController().navigate(R.id.action_menuViewFragment_to_gameSceneFragment)
            }

            tvCountOfMoney.text = coins.toString()
        }
    }
}