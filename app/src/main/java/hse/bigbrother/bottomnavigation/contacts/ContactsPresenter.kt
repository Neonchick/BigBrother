package hse.bigbrother.bottomnavigation.contacts

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import hse.bigbrother.bottomnavigation.contacts.service.TransmitterService
import hse.bigbrother.options.OptionsChecker
import hse.bigbrother.permissions.PermissionsHelper
import moxy.MvpPresenter
import javax.inject.Inject

class ContactsPresenter @Inject constructor(
    private val provideContext: Context,
    private val contactsRepository: ContactsRepository,
    private val permissionsHelper: PermissionsHelper,
    private val optionsChecker: OptionsChecker,
) : MvpPresenter<ContactsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getBeaconInfo()
    }

    fun reloadButtonClicked() {
        viewState.hideError()
        getBeaconInfo()
    }

    private fun getBeaconInfo() {
        viewState.showProgress()
        contactsRepository.getBeaconInfo(
            listener = {
                viewState.hideProgress()
                viewState.showContent()
            },
            errorListener = {
                viewState.hideProgress()
                viewState.showError()
            }
        )
    }

    fun onTransmitterSwitchCompatOn() {
        if (!permissionsHelper.hasPermissions()) {
            permissionsHelper.getPermissions()
            viewState.transmitterSwitchCompatOff()
            return
        }
        if (!optionsChecker.isAllNeededOptionsEnable()) {
            optionsChecker.checkOptions()
            viewState.transmitterSwitchCompatOff()
            return
        }
        ContextCompat.startForegroundService(
            provideContext,
            Intent(provideContext, TransmitterService::class.java)
        )
        contactsRepository.isTransmitterOn = true
        viewState.addListener()
    }

    fun onTransmitterSwitchCompatOff() {
        provideContext.stopService(Intent(provideContext, TransmitterService::class.java))
        contactsRepository.isTransmitterOn = false
        viewState.deleteListener()
    }

    fun getContactsList() = contactsRepository.connectionList

    fun isTransmitterOn() = contactsRepository.isTransmitterOn

}