package hse.bigbrother.bottomnavigation.scenarios

import hse.bigbrother.bottomnavigation.scenarios.data.Scenario
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface ScenariosView: MvpView {

    fun showProgress()

    fun hideProgress()

    fun showError()

    fun hideError()

    fun showContent(scenarios: List<Scenario>)

    fun hideContent()

}