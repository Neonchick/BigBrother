package hse.bigbrother.bottomnavigation.scenarios.recycleview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hse.bigbrother.R

class ScenarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var nameTextView: TextView? = null
    var scenarioImageView: ImageView? = null

    init {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        scenarioImageView = itemView.findViewById(R.id.scenarioImageView)
    }

}