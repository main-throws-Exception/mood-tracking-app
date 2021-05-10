package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.CurrentDayPresenter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class CurrentDayFragment : BaseFragment(), CurrentDayContract.View {

    private lateinit var presenter: CurrentDayPresenter
    private var rvEntries: RecyclerView? = null
    private var toolbar: Toolbar? = null

    private var backArrow: ImageView? = null
    private var forwardArrow: ImageView? = null
    private var selectedDay: TextView? = null

    // нахожусь в процессе познавания мира
    // надо научиться автоматически брать зону, а пока так
    private val zone: ZoneId = ZoneId.of("Europe/Moscow")
    private var currentDay = ZonedDateTime.now(zone)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.fragment_current_day__toolbar)

        backArrow = view.findViewById(R.id.fragment_current_day__back_arrow)
        forwardArrow = view.findViewById(R.id.fragment_current_day__forward_arrow)

        selectedDay = view.findViewById(R.id.fragment_current_day__selected_day)
        selectedDay!!.text = currentDay.dayOfMonth.toString() + " " + currentDay.month.toString()

        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entries)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)

        presenter = CurrentDayPresenter(this)
        presenter.getEntries(currentDay)

        backArrow!!.setOnClickListener{
            onDayChanged(back = true)
        }

        forwardArrow!!.setOnClickListener {
            onDayChanged(back = false)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_current_day
    }

    override fun onCurrentDayReady() {
        navigationPresenter.stopLoading()
    }

    override fun onEntriesRead(entries: ArrayList<Entry>) {
        if (entries.size == 0) {
            Toast.makeText(activity?.applicationContext, R.string.no_entries, Toast.LENGTH_SHORT).show()
        }
        rvEntries?.adapter = EntriesAdapter(entries)
    }

    override fun onDayChanged(back: Boolean) {
        navigationPresenter.startLoading()
        if (back) {
            currentDay = currentDay.minusDays(1)
        } else {
            currentDay = currentDay.plusDays(1)
        }
        selectedDay!!.text = currentDay.dayOfMonth.toString() + " " + currentDay.month.toString()
        presenter.getEntries(currentDay)
    }
}
