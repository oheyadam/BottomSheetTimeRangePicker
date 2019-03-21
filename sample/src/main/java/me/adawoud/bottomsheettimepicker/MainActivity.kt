package me.adawoud.bottomsheettimepicker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnTimeRangeSelectedListener {
    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTimeRangePicker.setOnClickListener {
            BottomSheetTimeRangePicker
                .tabLabels(startTabLabel = "Hello", endTabLabel = "World")
                .doneButtonLabel("Ok")
                .startTimeInitialHour(2)
                .startTimeInitialMinute(11)
                .endTimeInitialHour(10)
                .endTimeInitialMinute(22)
                .newInstance(this, DateFormat.is24HourFormat(this))
                .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
        }
    }

    override fun onTimeRangeSelected(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        var startHourString = startHour.toString()
        var startMinuteString = startMinute.toString()
        var endHourString = endHour.toString()
        var endMinuteString = endMinute.toString()
        when {
            startHour < 9 -> startHourString = startHour.toString().prependZero()
            startMinute < 9 -> startMinuteString = startMinute.toString().prependZero()
            endHour < 9 -> endHourString = endHour.toString().prependZero()
            endMinute < 9 -> endMinuteString = endMinute.toString().prependZero()
        }
        tvTimeRange.text = getString(
            R.string.chosen_time_range,
            startHourString,
            startMinuteString,
            endHourString,
            endMinuteString
        )
    }

    private fun String.prependZero(): String {
        return "0".plus(this)
    }

}