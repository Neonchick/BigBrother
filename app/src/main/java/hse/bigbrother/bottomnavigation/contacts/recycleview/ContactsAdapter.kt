package hse.bigbrother.bottomnavigation.contacts.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.contacts.data.BeaconConnect

class ContactsAdapter(private val beacons: List<BeaconConnect>) :
    RecyclerView.Adapter<ContactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_recycle_view_item, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.idTextView?.text = beacons[position].id
        holder.distanceTextView?.text = beacons[position].distance
        holder.timeTextView?.text = beacons[position].time
    }

    override fun getItemCount(): Int = beacons.size

}