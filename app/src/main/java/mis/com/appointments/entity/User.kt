package mis.com.appointments.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "user_table")
data class User(
    val name: String, @PrimaryKey val email: String,
    val password: String,
    val dateOfBirth: Date,
    val civilID: String
): Serializable