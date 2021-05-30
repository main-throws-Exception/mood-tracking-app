package com.mainthrowsexception.moodtrackingapp.ui.charts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mainthrowsexception.moodtrackingapp.R


class MoodPieChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_mood_pie_chart, container, false)
        val pieChart: PieChart = view.findViewById(R.id.chart__mood_pie)
        val moodList = ArrayList<PieEntry>()
        moodList.add(PieEntry(20f, "Terrible"))
        moodList.add(PieEntry(20f, "Bad"))
        moodList.add(PieEntry(10f, "Neutral"))
        moodList.add(PieEntry(15f, "Good"))
        moodList.add(PieEntry(35f, "Perfect"))

        val pieDataSet = PieDataSet(moodList, "Mood")

        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(view.context, R.color.red))
        colors.add(ContextCompat.getColor(view.context, R.color.orange))
        colors.add(ContextCompat.getColor(view.context, R.color.grey))
        colors.add(ContextCompat.getColor(view.context, R.color.yellow))
        colors.add(ContextCompat.getColor(view.context, R.color.green))

        pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        pieChart.description.isEnabled = false
        pieChart.centerText = "Mood"
        pieChart.data = pieData

        return view
    }
}
