package mis.com.appointments

import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_appointment.*
import mis.com.appointments.database.APTRoomDatabase
import mis.com.appointments.entity.Appointment
import mis.com.appointments.entity.dao.AppointmentDAO
import mis.com.appointments.repository.AppointmentRepository
import java.util.*

class AddAppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_appointment)
    }

    fun addAppointment(v: View) {

        val title = titleEditText.text.toString()
        val doctor = doctorEditText.text.toString()
        val date = appDateEditText.text.toString()

        var cancel = false

        if (TextUtils.isEmpty(title)) {
            titleEditText.error = getString(R.string.error_field_required)
            cancel = true
        }

        if (TextUtils.isEmpty(doctor)) {
            doctorEditText.error = getString(R.string.error_field_required)
            cancel = true
        }

        if (TextUtils.isEmpty(date)) {
            appDateEditText.error = getString(R.string.error_field_required)
            cancel = true
        }

        if (!cancel) {
            AddAppointmentTask(Appointment(title, doctor, Date(date), null)).execute()

        }
    }

    inner class AddAppointmentTask internal constructor(private val appointment: Appointment) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            val appointmentDAO: AppointmentDAO = APTRoomDatabase.getDatabase(applicationContext).appointmentDAO()
            AppointmentRepository(appointmentDAO).insert(appointment)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

    }
}
