package hse.bigbrother.di.modelus

import android.content.Context
import dagger.Module
import dagger.Provides
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import hse.bigbrother.ibeacon.PersonContactHandler
import hse.bigbrother.ibeacon.PersonContactTransmitter

@Module(includes = [RepositoriesModule::class])
class IBeaconModule {

    private var personContactTransmitter: PersonContactTransmitter? = null

    private var personContactHandler: PersonContactHandler? = null

    @Provides
    fun personContactTransmitter(
        context: Context,
        contactsRepository: ContactsRepository
    ): PersonContactTransmitter {
        if (personContactTransmitter == null) {
            personContactTransmitter = PersonContactTransmitter(context, contactsRepository)
        }
        return personContactTransmitter!!
    }

    @Provides
    fun personContactHandler(
        context: Context,
        contactsRepository: ContactsRepository
    ): PersonContactHandler {
        if (personContactHandler == null) {
            personContactHandler = PersonContactHandler(context, contactsRepository)
        }
        return personContactHandler!!
    }

}