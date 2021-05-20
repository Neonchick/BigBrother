package hse.bigbrother.bottomnavigation.contacts

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface ContactsView: MvpView {

    fun addListener()

    fun deleteListener()

    fun showProgress()

    fun hideProgress()

    fun showError()

    fun hideError()

    fun showContent()

    fun transmitterSwitchCompatOff()

}