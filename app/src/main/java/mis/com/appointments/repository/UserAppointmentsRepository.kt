package mis.com.appointments.repository

import androidx.annotation.WorkerThread
import mis.com.appointments.entity.UserAppointments
import mis.com.appointments.entity.dao.UserAndAllAppointmentsDAO

class UserAppointmentsRepository(private val userAndAllAppointmentsDAO: UserAndAllAppointmentsDAO) {
    @WorkerThread
    fun getAppointmentsOf(email: String): List<UserAppointments> {
        return userAndAllAppointmentsDAO.getAllUserAppointments(email)

    }
}