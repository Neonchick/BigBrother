package hse.bigbrother.bottomnavigation.profile

import android.content.Context
import android.content.Intent
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import hse.bigbrother.bottomnavigation.contacts.service.TransmitterService
import hse.bigbrother.bottomnavigation.scenarios.ScenariosRepository
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import moxy.MvpPresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val provideContext: Context,
    private val contactsRepository: ContactsRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val profileRepository: ProfileRepository,
    private val scenariosRepository: ScenariosRepository,
) : MvpPresenter<ProfileView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getUserInfo()
    }

    fun reloadButtonClicked() {
        viewState.hideError()
        getUserInfo()
    }

    private fun getUserInfo() {
        viewState.showProgress()
        profileRepository.getUserInfo(
            listener = { userInfo ->
                viewState.hideProgress()
                viewState.showContent(userInfo)
            },
            errorListener = {
                viewState.hideProgress()
                viewState.showError()
            }
        )
    }

    fun logoutButtonClicked() {
        provideContext.stopService(Intent(provideContext, TransmitterService::class.java))
        contactsRepository.deleteInfo()
        profileRepository.deleteInfo()
        scenariosRepository.deleteInfo()
        sharedPreferencesRepository.deleteToken()
        sharedPreferencesRepository.deleteUserId()
        viewState.logout()
    }

}