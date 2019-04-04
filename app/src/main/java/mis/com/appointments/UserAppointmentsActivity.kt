package mis.com.appointments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_choose_appointment.*
import kotlinx.android.synthetic.main.activity_user_appointments.*
import mis.com.appointments.Utils.Constant
import mis.com.appointments.database.APTRoomDatabase
import mis.com.appointments.entity.Appointment
import mis.com.appointments.entity.User
import mis.com.appointments.entity.UserAppointments
import mis.com.appointments.repository.AppointmentRepository
import mis.com.appointments.repository.UserAppointmentsRepository

class UserAppointmentsActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var appointments: List<UserAppointments>
    lateinit var adapter: UserAppointmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_appointments)

        user = intent.getSerializableExtra(Constant.NEW_USER_EXTRA_KEY) as User
        userName.text = Constant.formatName(user.name)

        adapter = UserAppointmentsAdapter(this)
        userAppointmentsList.adapter = adapter
        userAppointmentsList.layoutManager = LinearLayoutManager(this)

        getUserAppointments()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.CHOOSE_APPOINTMENT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null)
                assignAppointmentToUser(data.getSerializableExtra(Constant.CHOSEN_APPOINTMENT_KEY) as Appointment)

        }
    }

    private fun assignAppointmentToUser(appointment: Appointment) {
        Thread(Runnable {
            val appointmentDAO =
                APTRoomDatabase.getDatabase(applicationContext).appointmentDAO()
            appointment.userEmail = user.email
            AppointmentRepository(appointmentDAO).updateAppointment(appointment)

            getUserAppointments()
        }).start()
    }

    private fun getUserAppointments() {
        Thread(Runnable {
            val userAndAppointmentsDAO =
                APTRoomDatabase.getDatabase(applicationContext).userAppointmentsDAO()
            appointments = UserAppointmentsRepository(userAndAppointmentsDAO).getAppointmentsOf(user.email)

            runOnUiThread { adapter.setAppointmentsList(appointments[0].appointments) }
        }).start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_appointment -> {
                startActivity(Intent(this, AddAppointmentActivity::class.java))
                true
            }
            R.id.choose_appointment -> {
                startActivityForResult(
                    Intent(this, ChooseAppointmentActivity::class.java),
                    Constant.CHOOSE_APPOINTMENT_REQUEST_CODE
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
