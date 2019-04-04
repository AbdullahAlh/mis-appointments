package mis.com.appointments.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserAppointments(
    @Embedded val user: User,
    @Relation(
        parentColumn = "email",
        entityColumn = "userEmail",
        entity = Appointment::class
    ) val appointments: List<Appointment>
)