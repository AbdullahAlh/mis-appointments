package mis.com.appointments.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import mis.com.appointments.entity.User

@Dao
interface UserDAO {
    @Query("SELECT * from user_table")
    fun getAllUsers(): List<User>

    @Query("SELECT * from user_table WHERE email=:email")
    fun getUserWithEmail(email: String): User

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)
}