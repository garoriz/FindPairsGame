package com.garif.gamefindpairs.feature.gamescene.presentation

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garif.gamefindpairs.R
import com.garif.gamefindpairs.databinding.FragmentGameSceneBinding
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameSceneFragment : Fragment(R.layout.fragment_game_scene) {
    private lateinit var binding: FragmentGameSceneBinding
    private lateinit var cardIds: MutableList<Int>
    private lateinit var selectedCards: MutableList<Pair<ShapeableImageView, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardIds = mutableListOf(
            R.drawable.gem1,
            R.drawable.gem1,
            R.drawable.gem2,
            R.drawable.gem2,
            R.drawable.gem3,
            R.drawable.gem3,
            R.drawable.gem4,
            R.drawable.gem4,
            R.drawable.gem5,
            R.drawable.gem5,
            R.drawable.gem6,
            R.drawable.gem6,
            R.drawable.gem7,
            R.drawable.gem7,
            R.drawable.gem8,
            R.drawable.gem8,
            R.drawable.gem9,
            R.drawable.gem9,
            R.drawable.gem10,
            R.drawable.gem10,
        )
        cardIds.shuffle()

        selectedCards = mutableListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameSceneBinding.bind(view)

        with(binding) {
            setImageInCard(ivCard1, cardIds[0])
            setImageInCard(ivCard2, cardIds[1])
            setImageInCard(ivCard3, cardIds[2])
            setImageInCard(ivCard4, cardIds[3])
            setImageInCard(ivCard5, cardIds[4])
            setImageInCard(ivCard6, cardIds[5])
            setImageInCard(ivCard7, cardIds[6])
            setImageInCard(ivCard8, cardIds[7])
            setImageInCard(ivCard9, cardIds[8])
            setImageInCard(ivCard10, cardIds[9])
            setImageInCard(ivCard11, cardIds[10])
            setImageInCard(ivCard12, cardIds[11])
            setImageInCard(ivCard13, cardIds[12])
            setImageInCard(ivCard14, cardIds[13])
            setImageInCard(ivCard15, cardIds[14])
            setImageInCard(ivCard16, cardIds[15])
            setImageInCard(ivCard17, cardIds[16])
            setImageInCard(ivCard18, cardIds[17])
            setImageInCard(ivCard19, cardIds[18])
            setImageInCard(ivCard20, cardIds[19])
        }
    }

    private fun setImageInCard(card: ShapeableImageView, cardId: Int) {
        card.setOnClickListener {
            selectedCards.add(Pair(card, cardId))
            ResourcesCompat.getDrawable(
                resources,
                cardId,
                null
            )?.let { it1 ->
                card.setImageDrawableWithAnimation(it1)
                card.isClickable = false
            }

            checkSelectedCards()
        }
    }

    private fun checkSelectedCards() {
        if (selectedCards.size.mod(2) == 0 &&
            selectedCards[selectedCards.size - 1].second !=
            selectedCards[selectedCards.size - 2].second
        ) {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.transparent,
                null
            )?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(300)
                    selectedCards[selectedCards.size - 2].first.setImageDrawableWithAnimation(
                        it
                    )
                    selectedCards[selectedCards.size - 1].first.setImageDrawableWithAnimation(
                        it
                    )
                    selectedCards[selectedCards.size - 2].first.isClickable = true
                    selectedCards[selectedCards.size - 1].first.isClickable = true
                    selectedCards.removeAt(selectedCards.size - 1)
                    selectedCards.removeAt(selectedCards.size - 1)
                }
            }
        } else if (selectedCards.size == 4) {
            view?.findNavController()
                ?.navigate(R.id.action_gameSceneFragment_to_endGamePopupFragment)
        }
    }

    private fun ImageView.setImageDrawableWithAnimation(drawable: Drawable, duration: Int = 300) {
        val currentDrawable = getDrawable()
        if (currentDrawable == null) {
            setImageDrawable(drawable)
            return
        }

        val transitionDrawable = TransitionDrawable(
            arrayOf(
                currentDrawable,
                drawable
            )
        )
        setImageDrawable(transitionDrawable)
        transitionDrawable.startTransition(duration)
        transitionDrawable.isCrossFadeEnabled = true
    }
}