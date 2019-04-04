package mis.com.appointments.Utils

import java.text.DateFormat
import java.util.*

object Constant {
    const val NEW_USER_EXTRA_KEY = "NEW_USER_E_K"
    const val USER_EXTRA_KEY = "USER_EXTRA_KEY"
    const val CHOOSE_APPOINTMENT_REQUEST_CODE = 1236
    const val CHOSEN_APPOINTMENT_KEY = "CHOSEN_APPOINTMENT_KEY"

    fun formatDate(date: Date): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)

        return df.format(date)
    }

    fun formatName(name: String): String {
        return "Welcome $name"
    }
}