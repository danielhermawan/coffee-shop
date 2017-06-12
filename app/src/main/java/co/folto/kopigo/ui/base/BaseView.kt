package co.folto.kopigo.ui.base

/**
 * Created by Daniel on 5/23/2017 for GitFInder project.
 */
interface BaseView<T>{

    fun attachPresenter(presenter: T)
}