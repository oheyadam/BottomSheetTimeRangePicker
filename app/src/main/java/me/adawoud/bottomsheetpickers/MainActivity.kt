package me.adawoud.bottomsheetpickers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomSheetTimeRangePicker.OnTimeRangeSelectedListener {
    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnTimeRangePicker.setOnClickListener {
            BottomSheetTimeRangePicker.newInstance(this, DateFormat.is24HourFormat(this))
                .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
        }
    }

    override fun onTimeRangeSelected(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        tvTimeRange.text = getString(R.string.chosen_time_range, startHour, startMinute, endHour, endMinute)
    }
}
