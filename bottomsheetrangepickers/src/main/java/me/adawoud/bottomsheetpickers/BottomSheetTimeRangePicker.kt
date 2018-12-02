package me.adawoud.bottomsheetpickers

import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.time_range_picker.*
import me.adawoud.BottomSheetRangePickers.R

@Suppress("DEPRECATION") // We deal with it below
class BottomSheetTimeRangePicker : BottomSheetDialogFragment() {
    private lateinit var listener: OnTimeRangeSelectedListener
    private var is24HourMode = false
    private var startHour = 0
    private var startMinute = 0
    private var endHour = 0
    private var endMinute = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.time_range_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Restore state on rotation
        savedInstanceState?.let { bundle ->
            startHour = bundle.getInt(KEY_START_HOUR)
            startMinute = bundle.getInt(KEY_START_MINUTE)
            endHour = bundle.getInt(KEY_END_HOUR)
            endMinute = bundle.getInt(KEY_END_MINUTE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startTimePicker.hour = startHour
                startTimePicker.minute = startMinute
                endTimePicker.hour = endHour
                endTimePicker.minute = endMinute
            } else {
                startTimePicker.currentHour = startHour
                startTimePicker.currentMinute = startMinute
                endTimePicker.currentHour = endHour
                endTimePicker.currentMinute = endMinute
            }
        }

        // Set whether to display the TimePickers in 12-hour or 24-hour modes.
        startTimePicker.setIs24HourView(is24HourMode)
        endTimePicker.setIs24HourView(is24HourMode)

        // Setup the TabHost
        tabHost.setup()
        val tabSpec1 = tabHost.newTabSpec(TAG_START_TIME)
        tabSpec1.setContent(R.id.startTimePicker)
        tabSpec1.setIndicator(getString(R.string.start_time))
        val tabSpec2 = tabHost.newTabSpec(TAG_END_TIME)
        tabSpec2.setContent(R.id.endTimePicker)
        tabSpec2.setIndicator(getString(R.string.end_time))
        tabHost.addTab(tabSpec1)
        tabHost.addTab(tabSpec2)
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        } else {
            btnSetTimeRange.setOnClickListener {
                /**
                 * Update the TimePicker values. We also save these same variables in
                 * {@link #onSaveInstanceState}, so when the Picker is started again, it has the same values
                 * picked last time by the user.
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startHour = startTimePicker.hour
                    startMinute = startTimePicker.minute
                    endHour = endTimePicker.hour
                    endMinute = endTimePicker.minute
                } else {
                    startHour = startTimePicker.currentHour
                    startMinute = startTimePicker.currentMinute
                    endHour = endTimePicker.currentHour
                    endMinute = endTimePicker.currentMinute
                }
                // Pass these values to the calling Activity/Fragment
                listener.onTimeRangeSelected(startHour, startMinute, endHour, endMinute)

                // Dismiss the Picker
                dismiss()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        btnSetTimeRange.setOnClickListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_START_HOUR, startHour)
        outState.putInt(KEY_START_MINUTE, startMinute)
        outState.putInt(KEY_END_HOUR, endHour)
        outState.putInt(KEY_END_MINUTE, endMinute)
    }

    interface OnTimeRangeSelectedListener {

        fun onTimeRangeSelected(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int)
    }

    companion object {
        // Constant keys for saving state
        private const val KEY_START_HOUR = "KEY_START_HOUR"
        private const val KEY_START_MINUTE = "KEY_START_MINUTE"
        private const val KEY_END_HOUR = "KEY_END_HOUR"
        private const val KEY_END_MINUTE = "KEY_END_MINUTE"
        // Tags for the Tabs
        private const val TAG_START_TIME = "TAG_START_TIME"
        private const val TAG_END_TIME = "TAG_END_TIME"

        /**
         * Returns a TimeRangePicker that's displayed as a BottomSheetDialog
         *
         * @param onTimeRangeSelectedListener the listener that's triggered when the user selects a time range.
         * @param is24HourMode tells the TimePickers whether they should be in 12-hour or 24-hour mode.
         *
         * @return a TimeRangePicker instance with the necessary callback and correct mode.
         * @see BottomSheetDialogFragment
         */
        fun newInstance(
            onTimeRangeSelectedListener: OnTimeRangeSelectedListener,
            is24HourMode: Boolean
        ): BottomSheetTimeRangePicker {
            val timeRangePicker = BottomSheetTimeRangePicker()
            timeRangePicker.listener = onTimeRangeSelectedListener
            timeRangePicker.is24HourMode = is24HourMode

            return timeRangePicker
        }
    }
}