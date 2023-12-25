package com.garif.gamefindpairs.feature.endgamepopup.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.garif.gamefindpairs.R
import com.garif.gamefindpairs.databinding.FragmentEndGamePopupBinding

class EndGamePopupFragment : Fragment(R.layout.fragment_end_game_popup) {
    private lateinit var binding: FragmentEndGamePopupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndGamePopupBinding.bind(view)

        with(binding) {
            tvMoney.text = arguments?.getInt("coins").toString()
        }
    }
}