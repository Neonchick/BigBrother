package hse.bigbrother.bottomnavigation.scenarios.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.scenarios.data.Scenario
import kotlinx.android.synthetic.main.fragment_scenario_dialog.*
import moxy.MvpAppCompatDialogFragment

class ScenarioDialogFragment : MvpAppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scenario_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        val scenario: Scenario = arguments?.getParcelable(SCENARIO)!!
        nameTextView.text = scenario.name
        roleTextView.text = scenario.role
        timeTextView.text = scenario.time
        peopleCountTextView.text = scenario.peopleCount?.toString() ?: ""
        descriptionTextView.text = scenario.description
        if (scenario.isSuccessStatus==true) {
            statusImageView.setImageResource(R.drawable.ic_baseline_check_circle_32)
        } else {
            statusImageView.setImageResource(R.drawable.ic_baseline_cancel_32)
        }
    }


    companion object {

        const val TAG = "ScenarioDialogFragment"

        const val SCENARIO = "Scenario"

        fun newInstance(scenario: Scenario): ScenarioDialogFragment {
            val scenarioDialogFragment = ScenarioDialogFragment()
            scenarioDialogFragment.arguments = Bundle()
            scenarioDialogFragment.arguments?.putParcelable(SCENARIO, scenario)
            return scenarioDialogFragment
        }

    }

}