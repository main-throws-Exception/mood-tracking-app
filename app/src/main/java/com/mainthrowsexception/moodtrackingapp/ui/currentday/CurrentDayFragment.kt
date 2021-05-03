package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.CurrentDayPresenter


class CurrentDayFragment : BaseFragment(), CurrentDayContract.View {

    private lateinit var presenter: CurrentDayPresenter
    private var rvEntries: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entries)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)

        presenter = CurrentDayPresenter(this, rvEntries!!)
        presenter.getEntries()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_current_day
    }
}
