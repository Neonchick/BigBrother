package hse.bigbrother.bottomnavigation.scenarios.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.scenarios.data.Scenario

class ScenariosAdapter(
    private val scenarioClickListener: ScenarioClickListener
) : RecyclerView.Adapter<ScenarioViewHolder>() {

    var scenarios: List<Scenario> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScenarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.scenarios_recycle_view_item, parent, false)
        return ScenarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScenarioViewHolder, position: Int) {
        holder.nameTextView?.text = scenarios[position].name
        if (scenarios[position].isSuccessStatus == true) {
            holder.scenarioImageView?.setImageResource(R.drawable.ic_baseline_check_circle_32)
        } else {
            holder.scenarioImageView?.setImageResource(R.drawable.ic_baseline_cancel_32)
        }
        holder.itemView.setOnClickListener {
            scenarioClickListener.onScenarioClick(scenarios[position])
        }
    }

    override fun getItemCount(): Int = scenarios.size

}