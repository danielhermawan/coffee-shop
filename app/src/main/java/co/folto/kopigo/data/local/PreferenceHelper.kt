package co.folto.kopigo.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 5/23/2017 for GitFInder project.
 */
@Singleton
class PreferenceHelper @Inject constructor(private val pref: SharedPreferences) {
    private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    private val PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"

    fun saveToken(accessToken: String, refreshToken: String) {
        pref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken)
                .putString(PREF_KEY_REFRESH_TOKEN, refreshToken)
                .apply()
    }

    fun getAccessToken() = pref.getString(PREF_KEY_ACCESS_TOKEN, "")

    fun clear() {
        pref.edit().clear().apply()
    }
}