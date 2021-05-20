package hse.bigbrother.bottomnavigation.profile

import hse.bigbrother.bottomnavigation.profile.data.UserInfo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface ProfileView: MvpView {

    fun logout()

    fun showProgress()

    fun hideProgress()

    fun showError()

    fun hideError()

    fun showContent(userInfo: UserInfo)

}