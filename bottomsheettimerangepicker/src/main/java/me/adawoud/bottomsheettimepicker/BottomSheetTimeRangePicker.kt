package me.adawoud.bottomsheettimepicker

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.time_range_picker.*
import me.adawoud.bottomsheetpickers.R

@Suppress("DEPRECATION") // We deal with it below
class BottomSheetTimeRangePicker : BottomSheetDialogFragment() {
    private lateinit var listener: OnTimeRangeSelectedListener
    private lateinit var startTimeText: String
    private lateinit var endTimeText: String
    private lateinit var doneButtonText: String
    private var is24HourMode = false
    private var startHour = -1
    private var startMinute = -1
    private var endHour = -1
    private var endMinute = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.time_range_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Restore state on rotation
        savedInstanceState?.let { bundle ->
            startHour = bundle.getInt(KEY_START_HOUR)
            startMinute = bundle.getInt(KEY_START_MINUTE)
            endHour = bundle.getInt(KEY_END_HOUR)
            endMinute = bundle.getInt(KEY_END_MINUTE)
            setInitialValuesBasedOnSdkLevel(
                actionIfSdkLevelIsHigherThanOrEqualToM = {
                    startTimePicker.hour = startHour
                    startTimePicker.minute = startMinute
                    endTimePicker.hour = endHour
                    endTimePicker.minute = endMinute
                }, actionIfSdkLevelIsLowerThanM = {
                    startTimePicker.currentHour = startHour
                    startTimePicker.currentMinute = startMinute
                    endTimePicker.currentHour = endHour
                    endTimePicker.currentMinute = endMinute
                })
        }

        // Set whether to display the TimePickers in 12-hour or 24-hour modes.
        startTimePicker.setIs24HourView(is24HourMode)
        endTimePicker.setIs24HourView(is24HourMode)

        initInitialTimeValuesIfAdjusted()
        initTabHost()

        // Change the Done button text label if the user has set it to something else
        if (::doneButtonText.isInitialized) {
            btnSetTimeRange.text = doneButtonText
        }

        startTimePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            startHour = hourOfDay
            startMinute = minute
        }

        endTimePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            endHour = hourOfDay
            endMinute = minute
        }
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        } else {
            btnSetTimeRange.setOnClickListener {
                // Pass these values to the calling Activity/Fragment
                if (::listener.isInitialized) {
                    setInitialValuesBasedOnSdkLevel(
                        actionIfSdkLevelIsHigherThanOrEqualToM = {
                            listener.onTimeRangeSelected(
                                startTimePicker.hour,
                                startTimePicker.minute,
                                endTimePicker.hour,
                                endTimePicker.minute
                            )
                        },
                        actionIfSdkLevelIsLowerThanM = {
                            listener.onTimeRangeSelected(
                                startTimePicker.currentHour,
                                startTimePicker.currentMinute,
                                endTimePicker.currentHour,
                                endTimePicker.currentMinute
                            )
                        })
                }
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

    // Setup the TabHost
    private fun initTabHost() {
        tabHost.setup()
        val tabSpec1: TabHost.TabSpec = tabHost.newTabSpec(TAG_START_TIME)
        tabSpec1.setContent(R.id.startTimePicker)
        if (::startTimeText.isInitialized) {
            tabSpec1.setIndicator(startTimeText)
        } else {
            tabSpec1.setIndicator(getString(R.string.start_time))
        }
        val tabSpec2: TabHost.TabSpec = tabHost.newTabSpec(TAG_END_TIME)
        tabSpec2.setContent(R.id.endTimePicker)
        if (::endTimeText.isInitialized) {
            tabSpec2.setIndicator(endTimeText)
        } else {
            tabSpec2.setIndicator(getString(R.string.end_time))
        }
        tabHost.addTab(tabSpec1)
        tabHost.addTab(tabSpec2)
    }

    // Set initial values if the user has changed them before initializing
    private fun initInitialTimeValuesIfAdjusted() {
        doActionIfValueIsNotNegative(startHour, action = {
            setInitialValuesBasedOnSdkLevel(
                actionIfSdkLevelIsHigherThanOrEqualToM = {
                    startTimePicker.hour = startHour
                },
                actionIfSdkLevelIsLowerThanM = {
                    startTimePicker.currentHour = startHour
                })
        })
        doActionIfValueIsNotNegative(startMinute, action = {
            setInitialValuesBasedOnSdkLevel(
                actionIfSdkLevelIsHigherThanOrEqualToM = {
                    startTimePicker.minute = startMinute
                },
                actionIfSdkLevelIsLowerThanM = {
                    startTimePicker.currentMinute = startMinute
                })
        })
        doActionIfValueIsNotNegative(endHour, action = {
            setInitialValuesBasedOnSdkLevel(
                actionIfSdkLevelIsHigherThanOrEqualToM = {
                    endTimePicker.hour = endHour
                },
                actionIfSdkLevelIsLowerThanM = {
                    endTimePicker.currentHour = endHour
                })

        })
        doActionIfValueIsNotNegative(endMinute, action = {
            setInitialValuesBasedOnSdkLevel(
                actionIfSdkLevelIsHigherThanOrEqualToM = {
                    endTimePicker.hour = endHour
                },
                actionIfSdkLevelIsLowerThanM = {
                    endTimePicker.currentHour = endMinute
                })
        })
    }

    private fun setStartTimeText(text: String) {
        throwExceptionIfTextIsBlankAndDoActionOtherwise(text, action = { startTimeText = text })
    }

    private fun setEndTimeText(text: String) {
        throwExceptionIfTextIsBlankAndDoActionOtherwise(text, action = { endTimeText = text })
    }

    private fun setDoneButtonText(text: String) {
        throwExceptionIfTextIsBlankAndDoActionOtherwise(text, action = { doneButtonText = text })
    }

    private fun setStartTimeInitialHour(hour: Int) {
        startHour = validateHourValue(hour)
    }

    private fun setStartTimeInitialMinute(minute: Int) {
        startMinute = validateMinuteValue(minute)
    }

    private fun setEndTimeInitialHour(hour: Int) {
        endHour = validateHourValue(hour)
    }

    private fun setEndTimeInitialMinute(minute: Int) {
        endMinute = validateMinuteValue(minute)
    }

    private fun throwExceptionIfTextIsBlankAndDoActionOtherwise(text: String, action: () -> Unit) {
        if (text.isBlank()) {
            throw IllegalArgumentException("The text label can't be empty")
        } else {
            action()
        }
    }

    private fun doActionIfValueIsNotNegative(value: Int, action: () -> Unit) {
        if (value != -1) {
            action()
        }
    }

    private fun setInitialValuesBasedOnSdkLevel(
        actionIfSdkLevelIsHigherThanOrEqualToM: () -> Unit,
        actionIfSdkLevelIsLowerThanM: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            actionIfSdkLevelIsHigherThanOrEqualToM()
        } else {
            actionIfSdkLevelIsLowerThanM()
        }
    }

    private fun validateHourValue(hour: Int): Int {
        return when {
            hour > 24 -> throw IllegalArgumentException("Hour value can't be more than 24")
            else -> hour
        }
    }

    private fun validateMinuteValue(minute: Int): Int {
        return when {
            minute > 60 -> throw IllegalArgumentException("Minute value can't be more than 60")
            else -> minute
        }
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
        private val timeRangePicker = BottomSheetTimeRangePicker()

        /**
         * Sets the text of the Start and End time tabs.
         * This is useful for internationalization
         *
         * @param startTabLabel the value you'd like to use as an indicator for the Start time tab. It can't be blank
         * @param endTabLabel the value you'd like to use as an indicator for the End time tab. It can't be blank
         * @throws IllegalArgumentException when the text value is blank
         */
        fun tabLabels(startTabLabel: String, endTabLabel: String): Companion {
            timeRangePicker.setStartTimeText(startTabLabel)
            timeRangePicker.setEndTimeText(endTabLabel)
            return this
        }

        /**
         * Sets the text of the Done button.
         * This is useful for internationalization
         *
         * @param text the value you'd like to use as an indicator. It can't be blank
         * @throws IllegalArgumentException when the text value is blank
         */
        fun doneButtonLabel(text: String): Companion {
            timeRangePicker.setDoneButtonText(text)
            return this
        }

        /**
         * Sets the initial value of the start time hour
         *
         * @param hour the initial value of the hour
         * @throws IllegalArgumentException when the passed hour is larger than 12 or 24, depending on whether
         * the device is using 24-hour time format
         */
        fun startTimeInitialHour(hour: Int): Companion {
            timeRangePicker.setStartTimeInitialHour(hour)
            return this
        }

        /**
         * Sets the initial value of the start time minute
         *
         * @param minute the initial value of the minute
         * @throws IllegalArgumentException when the passed minute is larger than 60
         */
        fun startTimeInitialMinute(minute: Int): Companion {
            timeRangePicker.setStartTimeInitialMinute(minute)
            return this
        }

        /**
         * Sets the initial value of the end time hour
         *
         * @param hour the initial value of the hour
         * @throws IllegalArgumentException when the passed hour is larger than 12 or 24, depending on whether
         * the device is using 24-hour time format
         */
        fun endTimeInitialHour(hour: Int): Companion {
            timeRangePicker.setEndTimeInitialHour(hour)
            return this
        }

        /**
         * Sets the initial value of the end time minute
         *
         * @param minute the initial value of the minute
         * @throws IllegalArgumentException when the passed minute is larger than 60
         */
        fun endTimeInitialMinute(minute: Int): Companion {
            timeRangePicker.setEndTimeInitialMinute(minute)
            return this
        }

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
            is24HourMode: Boolean = false
        ): BottomSheetTimeRangePicker {
            timeRangePicker.listener = onTimeRangeSelectedListener
            timeRangePicker.is24HourMode = is24HourMode
            return timeRangePicker
        }
    }
}
