package com.mainthrowsexception.moodtrackingapp.ui.entry.slider

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import com.mainthrowsexception.moodtrackingapp.ui.entry.EntryFragment
import java.math.BigInteger
import java.util.stream.Collectors
import java.util.stream.Stream

class EntrySlider(
    private val slider: Slider,
    private val entryFragment: EntryFragment,
    moodValue: Int,
    moodStateColorIds: MutableList<Int>,
) : Slider.OnChangeListener {

    private val sliderTrackSize = slider.valueTo
    private val moodColorsRGB: MutableList<MutableList<Float>> = getMoodRGBColorsByIds(moodStateColorIds)
    private val sliderDivisionAmount = moodColorsRGB.size - 1
    private val sliderDivisionsLength = sliderTrackSize / sliderDivisionAmount
    private val moodColorsCoef: MutableList<MutableList<Float>> = getMoodColorsCoef()
    private val moodPoints = Stream.iterate(0f, { it + sliderDivisionsLength })
        .limit(sliderDivisionAmount.toLong() + 1)
        .collect(Collectors.toList())

    init {
        slider.value = moodValue * sliderDivisionsLength
    }

    fun getCurrentMoodValue(): Int {
        return (slider.value / sliderDivisionsLength).toInt()
    }

    private fun getMoodColorsCoef(): MutableList<MutableList<Float>> {
        val moodColorsCoef = ArrayList<MutableList<Float>>()

        for (i in 1 until moodColorsRGB.size) {
            moodColorsCoef.add(ArrayList())
            for (j in 0..2) {
                val diff = moodColorsRGB[i-1][j] - moodColorsRGB[i][j]
                val coef = diff / sliderDivisionsLength
                moodColorsCoef[i-1].add(coef)
            }
        }
        return moodColorsCoef
    }

    init {
        slider.addOnChangeListener(this)
    }

    private fun getMoodRGBColorsByIds(ids: MutableList<Int>): MutableList<MutableList<Float>> {
        return ids.stream()
            .map {
                val colorInt = ContextCompat.getColor(entryFragment.requireContext(), it)
                val colorHex = String.format("#%06X", (0xffffff and colorInt))
                val splitColorHex = listOf(
                    colorHex.substring(1, 3),
                    colorHex.substring(3, 5),
                    colorHex.substring(5, 7)
                )
                mutableListOf(
                    BigInteger(splitColorHex[0], 16),
                    BigInteger(splitColorHex[1], 16),
                    BigInteger(splitColorHex[2], 16)
                ).stream()
                    .map { it.toString() }
                    .map { it.toFloat() }
                    .collect(Collectors.toList())
            }.collect(Collectors.toList())
    }

    private var mood: Int? = null
    private var intervalValue: Float? = null

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        determineMoodInterval(value)

        val color = getMoodColor(value)
        entryFragment.updateMoodState(mood!!, color)
    }

    private fun determineMoodInterval(value: Float) {
        for (i in 1 until moodPoints.size) {
            if (value >= moodPoints[i-1] && value < moodPoints[i]) {
                mood = i - 1
                intervalValue = value - moodPoints[i-1]
            }
        }
        if (value == moodPoints.last()) {
            mood = 4
        }
        if (mood == null || intervalValue == null) {
            throw Exception("Slider thumb is out of track range")
        }
    }

    private fun getMoodColor(value: Float): Int {
        val colorRGB =
            if (value == moodPoints.last()) {
                moodColorsRGB.last()
            } else {
                getColorRGB()
            }
        val colorHex = getColorHex(colorRGB)
        return Color.parseColor(colorHex)
    }

    private fun getColorRGB(): MutableList<Float> {
        val colorDiffRGB = moodColorsCoef[mood!!].stream()
            .map { it * intervalValue!! }
            .collect(Collectors.toList())

        val colorRGB = ArrayList<Float>()
        for (i in 0..2) {
            colorRGB.add(moodColorsRGB[mood!!][i] - colorDiffRGB[i])
        }
        return colorRGB
    }

    private fun getColorHex(colorRGB: MutableList<Float>): String {
        return "#" + colorRGB
            .map { it.toInt() }
            .map { it.toString() }
            .map { BigInteger(it).toString(16) }
            .reduce { color1, color2 -> color1 + color2 }
    }
}