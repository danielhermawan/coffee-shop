package co.folto.kopigo.ui.main

import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.annotation.ActivityScoped
import dagger.Component

/**
 * Created by Daniel on 6/15/2017 for Kopigo project.
 */
@ActivityScoped
@Component(dependencies = arrayOf(DataComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
