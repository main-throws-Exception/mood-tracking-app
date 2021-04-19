package com.mainthrowsexception.moodtrackingapp

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

class TagsPieChartsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_tags_pie_chart, container, false)
        val pieChart: PieChart = view.findViewById(R.id.chart__tags_pie)
        val pieDataSet = getDataSet(view)
        val pieData = PieData(pieDataSet)

        pieChart.description.isEnabled = false
        pieChart.centerText = "Tags"
        pieChart.data = pieData

        return view
    }

    fun getDataSet(view: View): PieDataSet {
        val tagsList = listOf(
            PieEntry(5f, "Home"),
            PieEntry(3f, "Weekend"),
            PieEntry(4f, "Friends"),
            PieEntry(8f, "Work"),
            PieEntry(1f, "Other")
        )

        val pieDataSet = PieDataSet(tagsList, "Tags")

        val colors = listOf(
            ContextCompat.getColor(view.context, R.color.red),
            ContextCompat.getColor(view.context, R.color.orange),
            ContextCompat.getColor(view.context, R.color.green),
            ContextCompat.getColor(view.context, R.color.yellow),
            ContextCompat.getColor(view.context, R.color.grey)
        )

        pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        return pieDataSet
    }
}