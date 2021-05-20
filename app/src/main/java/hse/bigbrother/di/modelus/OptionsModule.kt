package hse.bigbrother.di.modelus

import android.content.Context
import dagger.Module
import dagger.Provides
import hse.bigbrother.options.OptionsChecker

@Module(includes = [ContextModule::class])
class OptionsModule {

    private var optionsChecker: OptionsChecker? = null

    @Provides
    fun optionsChecker(context: Context): OptionsChecker {
        if (optionsChecker == null) {
            optionsChecker = OptionsChecker(context)
        }
        return optionsChecker!!
    }

}