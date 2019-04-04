package mis.com.appointments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_choose_appointment.*
import mis.com.appointments.Utils.Constant
import mis.com.appointments.database.APTRoomDatabase
import mis.com.appointments.entity.Appointment
import mis.com.appointments.repository.AppointmentRepository

class ChooseAppointmentActivity : AppCompatActivity(), OnItemClick {

    lateinit var appointments: List<Appointment>
    lateinit var adapter: AppointmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_appointment)

        adapter = AppointmentsAdapter(this)
        allAppointmentsList.adapter = adapter
        allAppointmentsList.layoutManager = LinearLayoutManager(this)

        getAllAppointments()
    }

    private fun getAllAppointments() {

        Thread(Runnable {
            val appointmentDAO =
                APTRoomDatabase.getDatabase(applicationContext).appointmentDAO()
            appointments = AppointmentRepository(appointmentDAO).allAppointments

            runOnUiThread { adapter.setAppointmentsList(appointments) }

        }).start()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent()
        intent.putExtra(Constant.CHOSEN_APPOINTMENT_KEY, appointments[position])
        setResult(AppCompatActivity.RESULT_OK, intent)
        finish()
    }
}
