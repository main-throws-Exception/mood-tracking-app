package com.mainthrowsexception.moodtrackingapp.screen.currentday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.entry.model.Entry
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import java.util.*


class CurrentDayFragment : Fragment() {

    private var rvEntries: RecyclerView? = null
    private val entries = generateEntryList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_current_day, container, false)
        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entires)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)
        rvEntries!!.adapter = EntriesAdapter(entries as MutableList<Entry>)

        return view
    }

    private fun generateEntryList(): List<Entry> {
        return listOf(
            Entry(
                EntryId(1),
                1,
                listOf("asd", "qwe"),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                2,
                listOf("zxc", "rtyrtyrty"),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf("tag", "another tag"),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                4,
                listOf("aaa"),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                5,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                2,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                2,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                4,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                2,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                4,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                2,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                4,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                4,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
            Entry(
                EntryId(1),
                1,
                listOf(),
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ),
        )
    }
}
