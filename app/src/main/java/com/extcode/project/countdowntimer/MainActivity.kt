package com.extcode.project.countdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.countdowntimer.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener,
    TimePickerFragment.DialogTimeListener {

    private lateinit var binding: ActivityMainBinding

    private var startTimesInMillis: Long = 600000
    private var mTimerRunning = false
    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimeLeftInMillis = startTimesInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartPause.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
        binding.tvCountdown.setOnClickListener(this)

        updateCountdown()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnStartPause -> if (mTimerRunning) pauseTimer() else startTimer()
            R.id.btnReset -> resetTimer()
            R.id.tvCountdown -> if (!mTimerRunning) getTime()
        }
    }

    private fun getTime() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val hoursInMillis: Long = (hourOfDay * 60 * 60 * 1000).toLong()
        val minutesInMills: Long = (minute * 60 * 1000).toLong()
        startTimesInMillis = hoursInMillis + minutesInMills
        resetTimer()
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(p0: Long) {
                mTimeLeftInMillis = p0
                updateCountdown()
            }

            override fun onFinish() {
                mTimerRunning = false
                binding.btnStartPause.text = "Start"
                binding.btnStartPause.visibility = View.INVISIBLE
                binding.btnReset.visibility = View.VISIBLE
            }
        }.start()

        mTimerRunning = true
        binding.btnStartPause.text = "Pause"
        binding.btnReset.visibility = View.INVISIBLE
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        binding.btnStartPause.text = "Start"
        binding.btnReset.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        mTimeLeftInMillis = startTimesInMillis
        updateCountdown()
        binding.btnReset.visibility = View.INVISIBLE
        binding.btnStartPause.visibility = View.VISIBLE
    }

    private fun updateCountdown() {
        val hours = (mTimeLeftInMillis / 1000).toInt() / 3600
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60 % 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String =
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        binding.tvCountdown.text = timeLeftFormatted
    }
}