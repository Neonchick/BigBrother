package hse.bigbrother.bottomnavigation.scenarios

import moxy.MvpPresenter
import javax.inject.Inject

class ScenariosPresenter @Inject constructor(
    private val scenariosRepository: ScenariosRepository,
) : MvpPresenter<ScenariosView>() {

    override fun attachView(view: ScenariosView?) {
        super.attachView(view)
        getScenarios()
    }

    fun reloadButtonClicked() {
        viewState.hideError()
        getScenarios()
    }

    private fun getScenarios() {
        viewState.hideContent()
        viewState.showProgress()
        scenariosRepository.getScenarios(
            listener = { scenarios ->
                viewState.hideProgress()
                viewState.showContent(scenarios)
            },
            errorListener = {
                viewState.hideProgress()
                viewState.showError()
            }
        )
    }


}