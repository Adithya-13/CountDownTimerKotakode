package com.extcode.project.countdowntimer

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var dialogTimeListener: DialogTimeListener? = null

    interface DialogTimeListener {
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogTimeListener = context as? DialogTimeListener
    }

    override fun onDetach() {
        super.onDetach()
        if (dialogTimeListener != null) dialogTimeListener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true
        return TimePickerDialog(activity as Context, this, hourOfDay, minute, formatHour24)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        dialogTimeListener?.onDialogTimeSet(tag, p1, p2)
    }
}