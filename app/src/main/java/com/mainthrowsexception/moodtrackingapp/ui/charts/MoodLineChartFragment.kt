package com.mainthrowsexception.moodtrackingapp.ui.charts

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mainthrowsexception.moodtrackingapp.R

class MoodLineChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {

        val view: View =  inflater.inflate(R.layout.fragment_mood_line_chart, container, false)
        val lineChart: LineChart = view.findViewById(R.id.fragment_mood_line_chart__chart)

        val moodList = listOf(
            Entry(1f, 75f),
            Entry(2f, 50f),
            Entry(3f, 50f),
            Entry(4f, 0f),
            Entry(5f, 25f),
            Entry(6f, 100f),
            Entry(7f, 75f),
        )

        val colors = listOf(
            ContextCompat.getColor(view.context, R.color.light_grey),
            ContextCompat.getColor(view.context, R.color.light_grey),
            ContextCompat.getColor(view.context, R.color.very_light_red),
            ContextCompat.getColor(view.context, R.color.light_orange),
            ContextCompat.getColor(view.context, R.color.light_green),
            ContextCompat.getColor(view.context, R.color.light_yellow),
        )

        val lineDataSet = LineDataSet(moodList, "Mood").apply {
            this.colors = colors
            setDrawValues(false)
            setDrawCircles(true)
            setDrawCircleHole(true)
            setCircleColor(Color.WHITE)
            circleHoleColor = Color.GRAY
            circleRadius = 15f
            circleHoleRadius = 8f
            lineWidth = 3f
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            description.isEnabled = false
        }

        lineChart.xAxis.apply {
            valueFormatter = AxisFormatter(AxisFormatter.AxisFlag.X)
            position = XAxis.XAxisPosition.BOTTOM
            spaceMin = 0.3f
        }

        lineChart.axisLeft.apply {
            valueFormatter = AxisFormatter(AxisFormatter.AxisFlag.Y)
            setDrawGridLines(false)
            granularity = 25f
            axisMinimum = 0f
            axisMaximum = 100f
        }

        lineChart.axisRight.apply {
            setDrawGridLines(false)
            setDrawGridLinesBehindData(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }

        return view
    }

    private class AxisFormatter(_axisFlag: AxisFlag) : ValueFormatter() {

        private val axisFlag = _axisFlag

        enum class AxisFlag {
            X, Y
        }

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return if (axisFlag == AxisFlag.X)
                getXAxisLabel(value)
            else
                getYAxisLabel(value)
        }

        private fun getXAxisLabel(value: Float): String {
            return when (value) {
                1f -> "Mo"
                2f -> "Tu"
                3f -> "We"
                4f -> "Th"
                5f -> "Fr"
                6f -> "Sa"
                7f -> "Su"
                else -> ""
            }
        }

        private fun getYAxisLabel(value: Float): String {
            return when (value) {
                0f -> "Terrible"
                25f -> "Bad"
                50f -> "Neutral"
                75f -> "Good"
                100f -> "Excellent"
                else -> ""
            }
        }
    }
}
