package hse.bigbrother.bottomnavigation.contacts.recycleview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hse.bigbrother.R

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var idTextView: TextView? = null
    var distanceTextView: TextView? = null
    var timeTextView: TextView? = null

    init {
        idTextView = itemView.findViewById(R.id.idTextView)
        distanceTextView = itemView.findViewById(R.id.distanceTextView)
        timeTextView = itemView.findViewById(R.id.timeTextView)
    }

}