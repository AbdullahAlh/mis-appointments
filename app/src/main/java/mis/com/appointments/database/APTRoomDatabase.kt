package mis.com.appointments.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mis.com.appointments.entity.Appointment
import mis.com.appointments.entity.User
import mis.com.appointments.entity.UserAppointments
import mis.com.appointments.entity.dao.AppointmentDAO
import mis.com.appointments.entity.dao.UserAndAllAppointmentsDAO
import mis.com.appointments.entity.dao.UserDAO

@Database(entities = arrayOf(User::class, Appointment::class), version = 1)
@TypeConverters(Converters::class)
abstract class APTRoomDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun appointmentDAO(): AppointmentDAO
    abstract fun userAppointmentsDAO(): UserAndAllAppointmentsDAO

    companion object {
        @Volatile
        private var INSTANCE: APTRoomDatabase? = null

        fun getDatabase(context: Context): APTRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    APTRoomDatabase::class.java,
                    "APT_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}