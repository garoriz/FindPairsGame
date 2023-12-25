package com.garif.gamefindpairs.feature.gamescene.presentation

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
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
    private val gameTime = 120000L
    private val countDownTimer = object : CountDownTimer(gameTime, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            val min = (millisUntilFinished / 1000 / 60)
            val sec = (millisUntilFinished / 1000 % 60)
            with(binding) {
                if (sec < 10) {
                    val timerString = "0$min:0$sec"
                    tvTimer.text = timerString
                } else {
                    val timerString = "0$min:$sec"
                    tvTimer.text = timerString
                }
            }
            coins = getCoins(millisUntilFinished)
        }

        override fun onFinish() {
            view?.findNavController()
                ?.navigate(R.id.action_gameSceneFragment_to_endGamePopupFragment)
        }
    }
    private lateinit var sharedPreferences: SharedPreferences
    private var coins = 100

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
        )
        cardIds.shuffle()

        selectedCards = mutableListOf()
        sharedPreferences =
            requireActivity().getSharedPreferences(getString(R.string.shared_preferences_name), 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameSceneBinding.bind(view)
        val coins = sharedPreferences.getInt("coins", 0)
        countDownTimer.start()

        with(binding) {
            tvCountOfMoney.text = coins.toString()
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
                    removeClickable()
                    delay(300)
                    selectedCards[selectedCards.size - 2].first.setImageDrawableWithAnimation(
                        it
                    )
                    selectedCards[selectedCards.size - 1].first.setImageDrawableWithAnimation(
                        it
                    )
                    selectedCards.removeAt(selectedCards.size - 1)
                    selectedCards.removeAt(selectedCards.size - 1)
                    setClickable()
                }
            }
        } else if (selectedCards.size == 12) {
            countDownTimer.cancel()
            val bundle = bundleOf("coins" to coins)
            view?.findNavController()
                ?.navigate(R.id.action_gameSceneFragment_to_endGamePopupFragment, bundle)
        }
    }

    private fun setClickable() {
        with(binding) {
            ivCard1.isClickable = true
            ivCard2.isClickable = true
            ivCard3.isClickable = true
            ivCard4.isClickable = true
            ivCard5.isClickable = true
            ivCard6.isClickable = true
            ivCard7.isClickable = true
            ivCard8.isClickable = true
            ivCard9.isClickable = true
            ivCard10.isClickable = true
            ivCard11.isClickable = true
            ivCard12.isClickable = true
        }
    }

    private fun removeClickable() {
        with(binding) {
            ivCard1.isClickable = false
            ivCard2.isClickable = false
            ivCard3.isClickable = false
            ivCard4.isClickable = false
            ivCard5.isClickable = false
            ivCard6.isClickable = false
            ivCard7.isClickable = false
            ivCard8.isClickable = false
            ivCard9.isClickable = false
            ivCard10.isClickable = false
            ivCard11.isClickable = false
            ivCard12.isClickable = false
        }
    }

    private fun getCoins(millisUntilFinished: Long): Int {
        val timeToEarnMaxMoney = 100000
        return if (millisUntilFinished >= timeToEarnMaxMoney) {
            100
        } else {
            val coins = 100 - ((timeToEarnMaxMoney - millisUntilFinished) / 1000 * 5)
            checkCoins(coins.toInt())
        }
    }

    private fun checkCoins(coins: Int): Int {
        if (coins < 10) {
            return 10
        }
        return coins
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