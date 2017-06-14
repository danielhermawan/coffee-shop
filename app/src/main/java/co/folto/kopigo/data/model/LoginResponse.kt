package co.folto.kopigo.data.model

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
data class LoginResponse(
    val tokenTypes: String,
    val expireIn: String,
    val accessToken: String,
    val refreshToken: String
)