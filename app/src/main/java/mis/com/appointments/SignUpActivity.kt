package mis.com.appointments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import mis.com.appointments.Utils.Constant
import mis.com.appointments.database.APTRoomDatabase
import mis.com.appointments.entity.User
import mis.com.appointments.entity.dao.UserDAO
import mis.com.appointments.repository.UserRepository
import java.util.*

/**
 * A login screen that offers login via email/password.
 */
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // Set up the login form.
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_up_button.setOnClickListener { attemptLogin() }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        val nameStr = name.text.toString()
        val civilIDStr = civilId.text.toString()
        val dateOfBirth = Date(dateOfBirth.text.toString())

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)

            saveUserTODB(
                User(
                    email = emailStr,
                    name = nameStr,
                    password = passwordStr,
                    civilID = civilIDStr,
                    dateOfBirth = dateOfBirth
                )
            )
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        signup_progress.visibility = if (show) View.VISIBLE else View.GONE
        signup_form.visibility = if (show) View.GONE else View.VISIBLE

    }

    private fun saveUserTODB(user: User) {
        Thread(Runnable {
            val userDAO: UserDAO = APTRoomDatabase.getDatabase(applicationContext).userDAO()
            UserRepository(userDAO).insert(user)

            runOnUiThread {
                showProgress(false)
                val intent = Intent(applicationContext, UserAppointmentsActivity::class.java)
                intent.putExtra(Constant.NEW_USER_EXTRA_KEY, user)
                startActivity(intent)
                finish()

            }
        }).start()
    }

}
