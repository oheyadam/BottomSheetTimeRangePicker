package me.adawoud.bottomsheetpickers

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.date_range_picker.*
import me.adawoud.BottomSheetRangePickers.R

class BottomSheetDateRangePicker : BottomSheetDialogFragment() {
    private lateinit var listener: OnDateRangeSelectedListener
    private lateinit var startDateIndicator: String
    private lateinit var endDateIndicator: String
    private var startDayOfMonth = 0
    private var startMonth = 0
    private var startYear = 0
    private var endDayOfMonth = 0
    private var endMonth = 0
    private var endYear = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.date_range_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore state on rotation
        savedInstanceState?.let { bundle ->
            startDayOfMonth = bundle.getInt(KEY_START_DAY)
            startMonth = bundle.getInt(KEY_START_MONTH)
            startYear = bundle.getInt(KEY_START_YEAR)
            // Update start date picker values
            startDatePicker.updateDate(startYear, startMonth, startDayOfMonth)

            endDayOfMonth = bundle.getInt(KEY_END_DAY)
            endMonth = bundle.getInt(KEY_END_MONTH)
            endYear = bundle.getInt(KEY_END_YEAR)
            // Update end date picker values
            endDatePicker.updateDate(endYear, endMonth, endDayOfMonth)
        }

        // Setup the TabHost
        tabHost.setup()
        val tabSpec1 = tabHost.newTabSpec(TAG_START_DATE)
        tabSpec1.setContent(R.id.startDatePicker)
        tabSpec1.setIndicator(startDateIndicator)
        val tabSpec2 = tabHost.newTabSpec(TAG_END_DATE)
        tabSpec2.setContent(R.id.endDatePicker)
        tabSpec2.setIndicator(endDateIndicator)
        tabHost.addTab(tabSpec1)
        tabHost.addTab(tabSpec2)
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        } else {
            btnSetDateRange.setOnClickListener {
                /**
                 * Update the local values. We also save these same variables in
                 * {@link #onSaveInstanceState}, so when the Picker is started again, it has the same values
                 * picked last time by the user.
                 */
                startDayOfMonth = startDatePicker.dayOfMonth
                startMonth = startDatePicker.month
                startYear = startDatePicker.year
                endDayOfMonth = endDatePicker.dayOfMonth
                endMonth = endDatePicker.month
                endYear = endDatePicker.year

                // Pass these values to the calling Activity/Fragment
                listener.onDateRangeSelected(startDayOfMonth, startMonth, startYear, endDayOfMonth, endMonth, endYear)

                // Dismiss the Picker
                dismiss()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        btnSetDateRange.setOnClickListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_START_DAY, startDayOfMonth)
        outState.putInt(KEY_START_MONTH, startMonth)
        outState.putInt(KEY_START_YEAR, startYear)
        outState.putInt(KEY_END_DAY, endDayOfMonth)
        outState.putInt(KEY_END_MONTH, endMonth)
        outState.putInt(KEY_END_YEAR, endYear)
    }

    /**
     * Sets the minimal date supported by this {@link DatePicker} in
     * milliseconds since January 1, 1970 00:00:00 in
     * {@link TimeZone#getDefault()} time zone.
     *
     * @param minDate The minimal supported date.
     */
    internal fun setMinDate(minDate: Long) {
        startDatePicker.minDate = minDate
        endDatePicker.minDate = minDate
    }

    /**
     * Sets the maximal date supported by this {@link DatePicker} in
     * milliseconds since January 1, 1970 00:00:00 in
     * {@link TimeZone#getDefault()} time zone.
     *
     * @param maxDate The maximal supported date.
     */
    internal fun setMaxDate(maxDate: Long) {
        startDatePicker.maxDate = maxDate
        endDatePicker.maxDate = maxDate
    }

    interface OnDateRangeSelectedListener {

        fun onDateRangeSelected(
            startDayOfMonth: Int, startMont: Int, startYear: Int,
            endDayOfMonth: Int, endMonth: Int, endYear: Int
        )
    }

    companion object {
        // Constant keys for saving state
        private const val KEY_START_DAY = "KEY_START_DAY"
        private const val KEY_START_MONTH = "KEY_START_MONTH"
        private const val KEY_START_YEAR = "KEY_START_YEAR"
        private const val KEY_END_DAY = "KEY_END_DAY"
        private const val KEY_END_MONTH = "KEY_END_MONTH"
        private const val KEY_END_YEAR = "KEY_END_YEAR"
        // Tags for the Tabs
        private const val TAG_START_DATE = "TAG_START_DATE"
        private const val TAG_END_DATE = "TAG_END_DATE"
        // Default values for Tab titles
        private const val defaultStartDateTabIndicator = "Start date"
        private const val defaultEndDateTabIndicator = "End date"
        private const val defaultDoneButtonText = "Done"


        /**
         * Returns a BottomSheetDateRangePicker instance that's displayed as a BottomSheetDialog
         *
         * @param onDateRangeSelectedListener the listener that's triggered when the user selects a time range.
         * @param startDateTabIndicator the title of the start date tab. This has a default value of "Start time", but
         * you can change it in cases like internationalization.
         * @param endDateTabIndicator the title of the end date tab. This has a default value of "End time", but
         * you can change it in cases like internationalization.
         * @param doneButtonText the text of the Done Button. Default value is done. You can change this in case
         * of internationalization.
         *
         * @return a BottomSheetDateRangePicker instance with the necessary callback and correct mode.
         * @see BottomSheetDialogFragment
         */
        fun newInstance(
            onDateRangeSelectedListener: OnDateRangeSelectedListener,
            startDateTabIndicator: String = defaultStartDateTabIndicator,
            endDateTabIndicator: String = defaultEndDateTabIndicator,
            doneButtonText: String = defaultDoneButtonText
        ): BottomSheetDateRangePicker {

            val dateRangePicker = BottomSheetDateRangePicker()
            dateRangePicker.listener = onDateRangeSelectedListener
            dateRangePicker.startDateIndicator = startDateTabIndicator
            dateRangePicker.endDateIndicator = endDateTabIndicator
            dateRangePicker.btnSetDateRange.text = doneButtonText
            return dateRangePicker
        }
    }
}