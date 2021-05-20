package hse.bigbrother.login

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface LoginView: MvpView {

    fun showError()

    fun showWrongData()

    fun showProgress()

    fun hideProgress()

    fun loginSuccess()

}