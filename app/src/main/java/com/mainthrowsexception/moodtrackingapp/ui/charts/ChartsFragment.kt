package com.mainthrowsexception.moodtrackingapp.ui.charts

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.ChartsContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.ChartsPresenter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.floor

class ChartsFragment : BaseFragment(), ChartsContract.View {

    private lateinit var presenter: ChartsPresenter

    private lateinit var moodPieChart: PieChart
    private lateinit var moodPieData: PieData
    private val moodList = ArrayList<PieEntry>()
    private val moodsColors = ArrayList<Int>()

    private lateinit var tagsPieChart: PieChart
    private lateinit var tagsPieData: PieData
    private val tagsList = ArrayList<PieEntry>()
    private val moodLineList = ArrayList<Entry>()
    private val tagsColors = ArrayList<Int>()

    private lateinit var daysOfWeek: Array<String>

    private lateinit var lineChart: LineChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (context == null) return
        super.onViewCreated(view, savedInstanceState)

        daysOfWeek = arrayOf(getString(R.string.monday), getString(R.string.tuesday),
            getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday),
            getString(R.string.saturday), getString(R.string.sunday))

        moodPieChart = view.findViewById(R.id.chart__mood_pie1)
        tagsPieChart = view.findViewById(R.id.chart__tags_pie1)
        lineChart = view.findViewById(R.id.chart__mood_line)

//        временно
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
            ContextCompat.getColor(view.context, R.color.very_light_grey),
            ContextCompat.getColor(view.context, R.color.very_light_grey),
            ContextCompat.getColor(view.context, R.color.light_red),
            ContextCompat.getColor(view.context, R.color.orange),
            ContextCompat.getColor(view.context, R.color.light_green),
            ContextCompat.getColor(view.context, R.color.yellow),
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
            valueFormatter =
                AxisFormatter(AxisFormatter.AxisFlag.X, daysOfWeek)
            position = XAxis.XAxisPosition.BOTTOM
            spaceMin = 0.3f
        }

        lineChart.axisLeft.apply {
            valueFormatter =
                AxisFormatter(AxisFormatter.AxisFlag.Y, daysOfWeek)
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

        moodsColors.add(ContextCompat.getColor(requireView().context, R.color.very_bad_mood))
        moodsColors.add(ContextCompat.getColor(requireView().context, R.color.bad_mood))
        moodsColors.add(ContextCompat.getColor(requireView().context, R.color.neutral_mood))
        moodsColors.add(ContextCompat.getColor(requireView().context, R.color.good_mood))
        moodsColors.add(ContextCompat.getColor(requireView().context, R.color.very_good_mood))

        tagsColors.add(ContextCompat.getColor(requireView().context, R.color.purple_200))
        tagsColors.add(ContextCompat.getColor(requireView().context, R.color.purple_500))
        tagsColors.add(ContextCompat.getColor(requireView().context, R.color.purple_700))
        tagsColors.add(ContextCompat.getColor(requireView().context, R.color.teal_200))
        tagsColors.add(ContextCompat.getColor(requireView().context, R.color.teal_700))

        presenter = ChartsPresenter(this)
        presenter.getData()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_charts
    }

    override fun onMoodsReady(moods: ArrayList<Float>) {
        if (context == null) return
        Log.i("ChartsFragment", "onMoodReady called")

        moodList.clear()
        moodsColors.clear()

        for (i in 0..4) {
            if (moods[i] != 0f) {
                moodList.add(PieEntry(moods[i], getMoodString(i)))
                moodsColors.add(getColor(i))
            }
        }

        val pieDataSet = PieDataSet(moodList, getString(R.string.mood))

        pieDataSet.colors = moodsColors
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        moodPieData = PieData(pieDataSet)
        moodPieChart.description.isEnabled = false
        moodPieChart.centerText = getString(R.string.mood)
        moodPieChart.data = moodPieData
        moodPieChart.invalidate()

        onChartsReady()
    }

    private fun getMoodString(num: Int): String {
        if (context == null) return ""
        return when (num) {
            0 -> getString(R.string.terrible_mood)
            1 -> getString(R.string.bad_mood)
            2 -> getString(R.string.neutral_mood)
            3 -> getString(R.string.good_mood)
            4 -> getString(R.string.perfect_mood)
            else -> ""
        }
    }

    private fun getColor(num: Int): Int {
        if (context == null) return 0
        return when (num) {
            0 -> ContextCompat.getColor(requireView().context, R.color.very_bad_mood)
            1 -> ContextCompat.getColor(requireView().context, R.color.bad_mood)
            2 -> ContextCompat.getColor(requireView().context, R.color.neutral_mood)
            3 -> ContextCompat.getColor(requireView().context, R.color.good_mood)
            4 -> ContextCompat.getColor(requireView().context, R.color.very_good_mood)
            else -> 0
        }
    }

    override fun onMoodLineReady(moodLines: ArrayList<Entry>) {
        Log.i("ChartsFragment", "onMoodLineReady called with data: " + moodLines.toString())

        moodLineList.clear()

        val lineDataSet = LineDataSet(moodLines, "Mood").apply {
            this.colors = colors
            setDrawValues(false)
            setDrawCircles(true)
            setDrawCircleHole(true)
            setCircleColor(Color.WHITE)
            circleHoleColor = Color.GRAY
            circleRadius = 5f
            circleHoleRadius = 2f
            lineWidth = 3f
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            description.isEnabled = false
        }

        lineChart.xAxis.apply {
            valueFormatter =
                AxisFormatter(AxisFormatter.AxisFlag.X, daysOfWeek)
            position = XAxis.XAxisPosition.BOTTOM
            spaceMin = 0.3f
        }

        lineChart.axisLeft.apply {
            valueFormatter =
                AxisFormatter(AxisFormatter.AxisFlag.Y, daysOfWeek)
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

        lineChart.invalidate()
    }

    override fun onTagsReady(tagsMap: HashMap<String, Int>) {
        if (context == null) return
        tagsList.clear()

//        отсортировать мапу по значениям и оставить топ-5 (?)
        for (tag in tagsMap) {
            tagsList.add(PieEntry(tag.value.toFloat(), tag.key))
        }

        val pieDataSet = PieDataSet(tagsList, getString(R.string.tags))

        pieDataSet.colors = tagsColors
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        tagsPieData = PieData(pieDataSet)
        tagsPieChart.description.isEnabled = false
        tagsPieChart.centerText = getString(R.string.tags)
        tagsPieChart.data = tagsPieData
        tagsPieChart.invalidate()
    }

    override fun onChartsReady() {
        navigationPresenter.stopLoading()
    }

    private class AxisFormatter(_axisFlag: AxisFlag, private val daysOfWeek: Array<String>) : ValueFormatter() {

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
            return daysOfWeek[floor((value + dayOffset) % 7).toInt()]
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

        companion object {
            private var dayOffset: Int = 0

            init {
                val calendar = Calendar.getInstance()
                calendar.time = Date()
                dayOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1
            }
        }
    }
}
