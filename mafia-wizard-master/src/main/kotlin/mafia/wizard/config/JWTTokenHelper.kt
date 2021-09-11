package mafia.wizard.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JWTTokenHelper {
    @Value("\${jwt.auth.app}")
     var appName: String? = null

    @Value("\${jwt.auth.secret_key}")
     var secretKey: String? = null

    @Value("\${jwt.auth.expires_in}")
     var expiresIn = 0

    private val SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256

    private fun getAllClaimsFromToken(token: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }


    fun getUsernameFromToken(token: String): String? {
      return try {
            val claims = getAllClaimsFromToken(token)
            claims!!.subject
        } catch (e: Exception) {
            null
        }
    }

    @Throws(InvalidKeySpecException::class, NoSuchAlgorithmException::class)
    fun generateToken(username: String?): String? {
        return Jwts.builder()
            .setIssuer(appName)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(generateExpirationDate())
            .signWith(SIGNATURE_ALGORITHM, secretKey)
            .compact()
    }

    private fun generateExpirationDate(): Date? {
        return Date(Date().time + expiresIn * 1000)
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username != null && username == userDetails.username &&
                !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean {
        val expireDate = getExpirationDate(token)
        return expireDate!!.before(Date())
    }


    private fun getExpirationDate(token: String): Date? {
    return try {
            val claims = getAllClaimsFromToken(token)
            claims!!.expiration
        } catch (e: Exception) {
            null
        }
    }


    fun getIssuedAtDateFromToken(token: String): Date? {
        return try {
            val claims = getAllClaimsFromToken(token)
            claims!!.issuedAt
        } catch (e: Exception) {
            null
        }
    }

    fun getToken(request: HttpServletRequest): String? {
        val authHeader = getAuthHeaderFromHeader(request)
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else null
    }

    fun getAuthHeaderFromHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }
}