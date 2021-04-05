package com.mainthrowsexception.moodtrackingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class CurrentDayFragment : Fragment() {

    var rvEntries : RecyclerView? = null
    val entries = generateEntryList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_current_day, container, false)
        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entires)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)
        rvEntries!!.adapter = EntriesAdapter(entries)

        return view
    }
}

private fun generateEntryList(): List<Entry> {
    return listOf(
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            2,
            listOf(),
            Date()
        ),
        Entry(
            2,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            4,
            listOf(),
            Date()
        ),
        Entry(
            5,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            2,
            listOf(),
            Date()
        ),
        Entry(
            2,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            3,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            4,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
        Entry(
            1,
            listOf(),
            Date()
        ),
    )
}