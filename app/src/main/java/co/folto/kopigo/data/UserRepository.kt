package co.folto.kopigo.data

import co.folto.kopigo.data.contract.UserContract
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
    private val preferenceHelper: PreferenceHelper
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
        return kopigoService.logout()
            .doOnComplete {
                preferenceHelper.clear()
            }
    }

    override fun clearData() {
        preferenceHelper.clear()
    }

    override fun getUserProducts(): Flowable<List<Product>> {
        return kopigoService.getUserProduct()
    }
}