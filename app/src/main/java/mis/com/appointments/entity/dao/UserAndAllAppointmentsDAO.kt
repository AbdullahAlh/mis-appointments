package mis.com.appointments.entity.dao

import androidx.room.Dao
import androidx.room.Query
import mis.com.appointments.entity.UserAppointments

@Dao
interface UserAndAllAppointmentsDAO {
    @Query("SELECT * from user_table WHERE email=:email")
    fun getAllUserAppointments(email: String): List<UserAppointments>

}