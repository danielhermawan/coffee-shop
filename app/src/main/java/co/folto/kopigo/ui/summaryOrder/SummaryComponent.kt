package co.folto.kopigo.ui.summaryOrder

import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.annotation.ActivityScoped
import dagger.Component

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
@ActivityScoped
@Component(dependencies = arrayOf(DataComponent::class), modules = arrayOf(SummaryModule::class))
interface SummaryComponent {

    fun inject(summaryActivity: SummaryActivity)
}