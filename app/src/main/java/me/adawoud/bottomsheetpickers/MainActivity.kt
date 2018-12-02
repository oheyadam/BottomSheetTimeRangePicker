package me.adawoud.bottomsheetpickers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomSheetTimeRangePicker.OnTimeRangeSelectedListener,
    BottomSheetDateRangePicker.OnDateRangeSelectedListener {
    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"
    private val tagBottomSheetDateRangePicker = "tagBottomSheetDateRangePicker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        btnTimeRangePicker.setOnClickListener {
            BottomSheetTimeRangePicker
                .newInstance(this, DateFormat.is24HourFormat(this))
                .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
        }
        btnDateRangePicker.setOnClickListener {
            BottomSheetDateRangePicker.newInstance(this)
                .show(supportFragmentManager, tagBottomSheetDateRangePicker)
        }
    }

    override fun onStop() {
        super.onStop()
        btnTimeRangePicker.setOnClickListener(null)
        btnDateRangePicker.setOnClickListener(null)
    }

    override fun onTimeRangeSelected(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        tvTimeRange.text = getString(R.string.chosen_time_range, startHour, startMinute, endHour, endMinute)
    }

    override fun onDateRangeSelected(
        startDayOfMonth: Int,
        startMonth: Int,
        startYear: Int,
        endDayOfMonth: Int,
        endMonth: Int,
        endYear: Int
    ) {
        tvDateRange.text = getString(
            R.string.chosen_date_range, startMonth, startDayOfMonth, startYear,
            endMonth, endDayOfMonth, endYear
        )
    }
}
