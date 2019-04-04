package mis.com.appointments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import mis.com.appointments.Utils.Constant
import mis.com.appointments.database.APTRoomDatabase
import mis.com.appointments.entity.dao.UserDAO
import mis.com.appointments.repository.UserRepository

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lpassword.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    fun openSignUpActivity(v: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        // Reset errors.
        lemail.error = null
        lpassword.error = null

        // Store values at the time of the login attempt.
        val emailStr = lemail.text.toString()
        val passwordStr = lpassword.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            lpassword.error = getString(R.string.error_invalid_password)
            focusView = lpassword
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            lemail.error = getString(R.string.error_field_required)
            focusView = lemail
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            lemail.error = getString(R.string.error_invalid_email)
            focusView = lemail
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
            getUserFromDB(emailStr, passwordStr)
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
        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_form.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun getUserFromDB(email: String, password: String) {
        Thread(Runnable {
            val userDAO: UserDAO = APTRoomDatabase.getDatabase(applicationContext).userDAO()
            val user = UserRepository(userDAO).getUserWithEmail(email)


            runOnUiThread {
                showProgress(false)
                when {
                    user == null -> {
                        lemail.error = getString(R.string.error_invalid_email)
                        lemail.requestFocus()

                    }
                    user.password == password -> {
                        val intent = Intent(applicationContext, UserAppointmentsActivity::class.java)
                        intent.putExtra(Constant.NEW_USER_EXTRA_KEY, user)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        lpassword.error = getString(R.string.error_incorrect_password)
                        lpassword.requestFocus()
                    }
                }
            }
        }).start()

    }

}
