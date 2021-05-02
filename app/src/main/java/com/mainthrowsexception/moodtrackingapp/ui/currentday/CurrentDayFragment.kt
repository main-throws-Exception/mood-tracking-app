package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.util.Generator
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
        val generator = Generator()
        val entriesList: MutableList<Entry> = ArrayList()

        for (i in 0..generator.nextInt(25)) {
            entriesList.add(Entry(
                "0",
                "0",
                generator.string(20),
                generator.nextInt(5),
                System.currentTimeMillis() - generator.nextInt(100) * 1000,
                System.currentTimeMillis() - generator.nextInt(100) * 1000
            ))
        }

        return entriesList.sortedBy { it.created }
    }
}
