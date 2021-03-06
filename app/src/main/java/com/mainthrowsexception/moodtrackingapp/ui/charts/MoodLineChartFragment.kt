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
            Entry(1f, 3f),
            Entry(2f, 2f),
            Entry(3f, 2f),
            Entry(4f, 0f),
            Entry(5f, 1f),
            Entry(6f, 4f),
            Entry(7f, 3f),
        )

        val colors = listOf(
            ContextCompat.getColor(view.context, R.color.neutral),
            ContextCompat.getColor(view.context, R.color.neutral),
            ContextCompat.getColor(view.context, R.color.terrible),
            ContextCompat.getColor(view.context, R.color.bad),
            ContextCompat.getColor(view.context, R.color.light_green),
            ContextCompat.getColor(view.context, R.color.good),
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
            granularity = 1f
            axisMinimum = 0f
            axisMaximum = 4f
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
                0f -> "Mo"
                1f -> "Tu"
                2f -> "We"
                3f -> "Th"
                4f -> "Fr"
                5f -> "Sa"
                6f -> "Su"
                else -> ""
            }
        }

        private fun getYAxisLabel(value: Float): String {
            return when (value) {
                0f -> "Terrible"
                1f -> "Bad"
                2f -> "Neutral"
                3f -> "Good"
                4f -> "Excellent"
                else -> ""
            }
        }
    }
}
