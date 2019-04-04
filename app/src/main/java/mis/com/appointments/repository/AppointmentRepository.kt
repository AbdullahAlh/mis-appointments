package mis.com.appointments.repository

import androidx.annotation.WorkerThread
import mis.com.appointments.entity.Appointment
import mis.com.appointments.entity.dao.AppointmentDAO

class AppointmentRepository(private val appointmentDAO: AppointmentDAO) {

    val allAppointments = appointmentDAO.getAllAppointments()

    @WorkerThread
    fun insert(appointment: Appointment) {
        appointmentDAO.insertAppointment(appointment)
    }

    @WorkerThread
    fun updateAppointment(appointment: Appointment) {
        appointmentDAO.update(appointment)

    }

}