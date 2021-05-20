package hse.bigbrother.di.modelus

import dagger.Module
import dagger.Provides
import hse.bigbrother.permissions.PermissionsHelper

@Module
class PermissionsModule {

    private val permissionsHelper = PermissionsHelper()

    @Provides
    fun permissionsHelper() = permissionsHelper

}