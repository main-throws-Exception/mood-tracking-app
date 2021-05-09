package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.CurrentDayPresenter
import java.text.SimpleDateFormat
import java.util.*


class CurrentDayFragment : BaseFragment(), CurrentDayContract.View {

    private lateinit var presenter: CurrentDayPresenter
    private var rvEntries: RecyclerView? = null
    private var toolbar: Toolbar? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.fragment_current_day__toolbar)
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date  = sdf.format(today)
        toolbar!!.title = date

        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entries)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)

        presenter = CurrentDayPresenter(this)
        presenter.getEntries()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_current_day
    }

    override fun onCurrentDayReady() {
        navigationPresenter.stopLoading()
    }

    override fun onEntriesRead(entries: ArrayList<Entry>) {
        rvEntries?.adapter = EntriesAdapter(entries)
    }
}
