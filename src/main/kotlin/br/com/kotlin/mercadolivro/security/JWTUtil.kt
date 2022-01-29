package br.com.kotlin.mercadolivro.security

import br.com.kotlin.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.Subject

@Component
class JWTUtil {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expiration}")
    private val expitarion: Long? = null


    fun generateToken(id: String): String{
        return Jwts.builder()
            .setSubject(id)
            .setExpiration(Date(System.currentTimeMillis() + expitarion!!))
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)

        if(claims.subject == null || claims.expiration == null || Date().after(claims.expiration) ){
            return false
        }
        return true
    }
    
    fun getClaims(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        } catch (ex: Exception) {
            throw AuthenticationException("Token invalido", "999")
        }
    }

    fun getSubject(token: String ): String {
        return getClaims(token).subject
    }
}