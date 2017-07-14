package co.folto.kopigo.data

import co.folto.kopigo.data.contract.UserContract
import co.folto.kopigo.data.local.DatabaseService
import co.folto.kopigo.data.local.PreferenceHelper
import co.folto.kopigo.data.model.LoginResponse
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.remote.KopigoService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
@Singleton
class UserRepository @Inject constructor(
    private val kopigoService: KopigoService,
    private val preferenceHelper: PreferenceHelper,
    private val databaseService: DatabaseService
): UserContract {

    /**
     * @return Status whether user is login or not
     */
    override fun checkLoginStatus() = preferenceHelper.getAccessToken() != ""

    override fun login(username: String, password: String): Single<LoginResponse> {
        return kopigoService.login(username, password)
            .doAfterSuccess {
                preferenceHelper.saveToken(it.accessToken, it.refreshToken)
            }
    }

    override fun logout(): Completable {
        //preferenceHelper.clear()
        return databaseService.clearProducts()//.concatWith(kopigoService.logout())
    }

    override fun clearData(): Completable {
        preferenceHelper.clear()
        return databaseService.clearProducts()
    }

    //todo: change database source become more reactive using combinelatest or merge to call api
    //and database simoustely and can adapt realtime localdatabase change using this trick
    //https://github.com/kaushikgopal/RxJava-Android-Samples#9-pseudo-caching--retrieve-data-first-from-a-cache-then-a-network-call-using-concat-concateager-merge-or-publish
    override fun getUserProducts(): Flowable<List<Product>> {
        val databaseSource = databaseService.getProducts().take(1)
        val apiSource = kopigoService.getUserProduct()
            .doOnNext { databaseService.saveProducts(it) }
            .onErrorResumeNext{t: Throwable -> getProductsRecoveryObservable(t)}
        return Flowable.concat(databaseSource, apiSource)
    }

    //todo: fix error empty
    private fun getProductsRecoveryObservable(error: Throwable): Flowable<List<Product>>? {
        return databaseService.getProducts().take(1)
                .switchMap({
                    var result = Flowable.just(it)
                    if(it.count() == 0)
                        result = Flowable.error(error)
                    result
                })
    }
}