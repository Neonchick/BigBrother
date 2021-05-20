package hse.bigbrother.di.modelus

import dagger.Module
import dagger.Provides
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import hse.bigbrother.bottomnavigation.profile.ProfileRepository
import hse.bigbrother.bottomnavigation.scenarios.ScenariosRepository
import hse.bigbrother.volley.VolleyRequests

@Module(includes = [VolleyModule::class])
class RepositoriesModule {

    private var contactsRepository: ContactsRepository? = null

    private var scenariosRepository: ScenariosRepository? = null

    private var profileRepository: ProfileRepository? = null

    @Provides
    fun contactsRepository(volleyRequests: VolleyRequests): ContactsRepository {
        if (contactsRepository == null) {
            contactsRepository = ContactsRepository(volleyRequests)
        }
        return contactsRepository!!
    }

    @Provides
    fun scenariosRepository(volleyRequests: VolleyRequests): ScenariosRepository {
        if (scenariosRepository == null) {
            scenariosRepository = ScenariosRepository(volleyRequests)
        }
        return scenariosRepository!!
    }

    @Provides
    fun profileRepository(volleyRequests: VolleyRequests): ProfileRepository {
        if (profileRepository == null) {
            profileRepository = ProfileRepository(volleyRequests)
        }
        return profileRepository!!
    }

}