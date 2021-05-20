package hse.bigbrother.bottomnavigation.scenarios

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hse.bigbrother.BigBrotherApplication
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.scenarios.data.Scenario
import hse.bigbrother.bottomnavigation.scenarios.dialog.ScenarioDialogFragment
import hse.bigbrother.bottomnavigation.scenarios.recycleview.ScenarioClickListener
import hse.bigbrother.bottomnavigation.scenarios.recycleview.ScenariosAdapter
import kotlinx.android.synthetic.main.fragment_scenarios.*
import kotlinx.android.synthetic.main.fragment_scenarios.progressBar
import kotlinx.android.synthetic.main.layout_load_error.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class ScenariosFragment : ScenariosView, ScenarioClickListener, MvpAppCompatFragment() {

    @InjectPresenter
    lateinit var presenter: ScenariosPresenter

    @Inject
    lateinit var presenterProvider: Provider<ScenariosPresenter>

    @ProvidePresenter
    fun providePresenter(): ScenariosPresenter = presenterProvider.get()

    override fun onAttach(context: Context) {
        (activity?.applicationContext as BigBrotherApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scenarios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reloadButton.setOnClickListener { presenter.reloadButtonClicked() }
        scenariosRecycleView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        scenariosRecycleView.adapter = ScenariosAdapter(scenarioClickListener = this)
    }

    override fun onScenarioClick(scenario: Scenario) {
        ScenarioDialogFragment.newInstance(scenario)
            .show(childFragmentManager, ScenarioDialogFragment.TAG)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showError() {
        loadErrorConstraintLayout.visibility = View.VISIBLE
    }

    override fun hideError() {
        loadErrorConstraintLayout.visibility = View.GONE
    }

    override fun showContent(scenarios: List<Scenario>) {
        (scenariosRecycleView.adapter as ScenariosAdapter).scenarios = scenarios
        contentLinearLayout.visibility = View.VISIBLE
    }

    override fun hideContent() {
        contentLinearLayout.visibility = View.GONE
    }

}