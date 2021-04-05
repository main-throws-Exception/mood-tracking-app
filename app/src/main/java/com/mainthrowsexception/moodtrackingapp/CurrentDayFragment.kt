package com.mainthrowsexception.moodtrackingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class CurrentDayFragment : Fragment() {

    private var rvEntries: RecyclerView? = null
    private val entries = generateEntryList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_current_day, container, false)
        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entires)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)
        rvEntries!!.adapter = EntriesAdapter(entries as MutableList<Entry>)

        return view
    }
}

private fun generateEntryList(): List<Entry> {
    return listOf(
        Entry(
            1,
            listOf("asd", "qwe"),
            Date()
        ),
        Entry(
            2,
            listOf("zxc", "rtyrtyrty"),
            Date()
        ),
        Entry(
            1,
            listOf("tag", "another tag"),
            Date()
        ),
        Entry(
            4,
            listOf("aaa"),
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
            4,
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
        )
    )
}