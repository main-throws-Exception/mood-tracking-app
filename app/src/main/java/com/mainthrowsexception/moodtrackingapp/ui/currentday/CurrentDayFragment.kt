package com.mainthrowsexception.moodtrackingapp.ui.currentday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.CurrentDayPresenter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class CurrentDayFragment : BaseFragment(), CurrentDayContract.View {

    private lateinit var presenter: CurrentDayPresenter
    private var rvEntries: RecyclerView? = null
    private var toolbar: Toolbar? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.fragment_current_day__toolbar)
        val today = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/mm/yyyy")
        val date  = sdf.format(today)
        toolbar!!.title = date

        rvEntries = view.findViewById(R.id.fragment_current_day__rv_entries)
        rvEntries!!.layoutManager = LinearLayoutManager(view.context)

        presenter = CurrentDayPresenter(this, rvEntries!!)
        presenter.getEntries()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_current_day
    }
}
