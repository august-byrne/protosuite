package com.example.protosuite.ui.timer

import android.content.Context
import android.content.SharedPreferences
import com.example.protosuite.ui.notes.TimerState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
    ) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.augustbyrne.protosuite.preferences"
        private const val PREF_KEY_TIMER = "timer"
        private const val PREF_KEY_TEMP_TIMER = "temp_timer"
        private const val PREF_KEY_TIMER_RUNNING = "timer_running"
        private const val PREF_KEY_TIMER_STATE = "timer_state"
        private const val PREF_KEY_NOTE_ID = "note_id"
        private const val PREF_KEY_ITEM_INDEX = "item_index"
        private const val PREF_KEY_PREVIOUS_UNIT = "previous_unit"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var timeInMillis: Long
        get() = pref.getLong(PREF_KEY_TIMER, 0)
        set(timer) = pref.edit().putLong(PREF_KEY_TIMER, timer).apply()

    var tempTimeInMillis: Long
        get() = pref.getLong(PREF_KEY_TEMP_TIMER, 0)
        set(temp) = pref.edit().putLong(PREF_KEY_TEMP_TIMER, temp).apply()

    var isTimerRunning: Boolean
        get() = pref.getBoolean(PREF_KEY_TIMER_RUNNING, false)
        set(isTimerRunning) = pref.edit().putBoolean(PREF_KEY_TIMER_RUNNING, isTimerRunning).apply()

    var timerState: TimerState
        get() = TimerState.values()[pref.getInt(PREF_KEY_TIMER_STATE, 0)]
        set(timerState) = pref.edit().putInt(PREF_KEY_TIMER_STATE, timerState.ordinal).apply()

    var noteId: Int
        get() = pref.getInt(PREF_KEY_NOTE_ID, 0)
        set(noteId) = pref.edit().putInt(PREF_KEY_NOTE_ID, noteId).apply()

    var itemIndex: Int
        get() = pref.getInt(PREF_KEY_ITEM_INDEX, 0)
        set(itemIndex) = pref.edit().putInt(PREF_KEY_ITEM_INDEX, itemIndex).apply()

    var lastUsedTimeUnit: Int
        get() = pref.getInt(PREF_KEY_PREVIOUS_UNIT, 0)
        set(unitOfTime) = pref.edit().putInt(PREF_KEY_PREVIOUS_UNIT, unitOfTime).apply()

}
