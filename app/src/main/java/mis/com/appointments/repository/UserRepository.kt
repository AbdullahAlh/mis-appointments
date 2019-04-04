package mis.com.appointments.repository

import androidx.annotation.WorkerThread
import mis.com.appointments.entity.User
import mis.com.appointments.entity.dao.UserDAO

class UserRepository(private val userDAO: UserDAO) {
    val allUsers = userDAO.getAllUsers()

    @WorkerThread
    fun insert(user: User) {
        userDAO.insertUser(user)
    }

    @WorkerThread
    fun getUserWithEmail(email: String): User {
        return userDAO.getUserWithEmail(email)
    }

}