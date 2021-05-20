package hse.bigbrother.di.modelus

import android.content.Context
import dagger.Module
import dagger.Provides
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import hse.bigbrother.volley.VolleyRequests

@Module(includes = [ContextModule::class, SharedPreferencesModule::class])
class VolleyModule {

    private var volleyRequests: VolleyRequests? = null

    @Provides
    fun volleyRequests(
        context: Context,
        sharedPreferencesRepository: SharedPreferencesRepository
    ): VolleyRequests {
        if (volleyRequests == null) {
            volleyRequests = VolleyRequests(context, sharedPreferencesRepository)
        }
        return volleyRequests!!
    }

}