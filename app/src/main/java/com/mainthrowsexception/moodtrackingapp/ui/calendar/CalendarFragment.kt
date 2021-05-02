package com.mainthrowsexception.moodtrackingapp.ui.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CalendarContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.CalendarPresenter
import org.naishadhparmar.zcustomcalendar.CustomCalendar
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener
import java.util.*

class CalendarFragment : BaseFragment(), CalendarContract.View, OnDateSelectedListener {

    private lateinit var presenter: CalendarPresenter
    private lateinit var calendar: CustomCalendar
    private var curToast: Toast? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = view.findViewById(R.id.fragment_calendar__calendar)
        calendar.setOnDateSelectedListener(this)

        presenter.initCalendar(calendar)

        presenter = CalendarPresenter(this)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_calendar
    }

    override fun onDateSelected(view: View?, selectedDate: Calendar?, desc: Any?) {
        val sDate: String = selectedDate?.get(Calendar.DAY_OF_MONTH).toString() +
                "/" + (selectedDate?.get(Calendar.MONTH)?.plus(1)).toString() +
                "/" + (selectedDate?.get(Calendar.YEAR)).toString()

        curToast?.cancel()
        curToast = Toast.makeText(activity?.applicationContext, sDate, Toast.LENGTH_SHORT)
        curToast?.show()
    }
}
