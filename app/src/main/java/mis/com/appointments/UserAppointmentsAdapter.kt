package mis.com.appointments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mis.com.appointments.Utils.Constant
import mis.com.appointments.entity.Appointment

class UserAppointmentsAdapter internal constructor(var context: Context) :
    RecyclerView.Adapter<UserAppointmentsAdapter.UserViewHolder>() {
    private var appointmentsList: List<Appointment> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        val appointment = appointmentsList[position]

        viewHolder.titleTextView.text = appointment.name
        viewHolder.doctorTextView.text = appointment.doctor
        viewHolder.dateTextView.text = Constant.formatDate(appointment.dateTime)


    }

    fun setAppointmentsList(appointmentsList: List<Appointment>) {
        this.appointmentsList = appointmentsList

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = appointmentsList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val doctorTextView: TextView = itemView.findViewById(R.id.doctorTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

    }
}