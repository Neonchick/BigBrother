package hse.bigbrother.di;

import dagger.Component
import hse.bigbrother.MainActivity
import hse.bigbrother.bottomnavigation.contacts.ContactsFragment
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import hse.bigbrother.bottomnavigation.contacts.service.TransmitterService
import hse.bigbrother.bottomnavigation.profile.ProfileFragment
import hse.bigbrother.bottomnavigation.scenarios.ScenariosFragment
import hse.bigbrother.di.modelus.IBeaconModule
import hse.bigbrother.di.modelus.OptionsModule
import hse.bigbrother.di.modelus.PermissionsModule
import hse.bigbrother.di.modelus.RepositoriesModule
import hse.bigbrother.ibeacon.PersonContactHandler
import hse.bigbrother.ibeacon.PersonContactTransmitter
import hse.bigbrother.login.LoginFragment
import hse.bigbrother.options.OptionsChecker
import hse.bigbrother.permissions.PermissionsHelper

@Component(
    modules = [
        RepositoriesModule::class,
        IBeaconModule::class,
        PermissionsModule::class,
        OptionsModule::class
    ]
)
interface AppComponent {

    fun contactsRepository(): ContactsRepository

    fun personContactTransmitter(): PersonContactTransmitter

    fun personContactHandler(): PersonContactHandler

    fun permissionsHelper(): PermissionsHelper

    fun optionsChecker(): OptionsChecker

    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)

    fun inject(contactsFragment: ContactsFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(scenariosFragment: ScenariosFragment)

    fun inject(transmitterService: TransmitterService)

}
