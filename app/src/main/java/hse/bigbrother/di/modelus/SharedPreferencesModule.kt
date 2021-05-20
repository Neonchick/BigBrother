package hse.bigbrother.di.modelus

import dagger.Module
import dagger.Provides
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository

@Module
class SharedPreferencesModule {

    private val sharedPreferencesRepository = SharedPreferencesRepository()

    @Provides
    fun sharedPreferencesRepository() = sharedPreferencesRepository

}