package mis.com.appointments.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import mis.com.appointments.entity.Appointment

@Dao
interface AppointmentDAO {
    @Query("SELECT * from appointment_table")
    fun getAllAppointments(): List<Appointment>

    @Insert(onConflict = REPLACE)
    fun insertAppointment(appointment: Appointment)

    @Update
    fun update(appointment: Appointment)
}