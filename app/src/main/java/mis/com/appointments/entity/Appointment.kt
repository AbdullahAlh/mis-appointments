package mis.com.appointments.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "appointment_table")
data class Appointment(@PrimaryKey val name: String, val doctor: String, val dateTime: Date, var userEmail: String?): Serializable