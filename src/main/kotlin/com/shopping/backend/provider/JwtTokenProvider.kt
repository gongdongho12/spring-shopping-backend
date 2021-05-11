package com.shopping.backend.provider

import com.shopping.backend.domain.account.UserPrincipal
import com.shopping.backend.service.ShoppingUserDetailService
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    companion object {
        private const val TOKEN_TTL = 60 * 60 * 1000L
    }

    @Autowired
    lateinit var userDetailService: ShoppingUserDetailService

    // 설정 파일에 등록해 두었습니다.
    @Value("\${spring.jwt.secret}")
    lateinit var secretKey: String

    // 비밀키를 Base64로 인코딩해 주었습니다.
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        println("secretKey ${secretKey}")
    }

    // 토큰 만들기
    fun createToken(authentication: Authentication?): String =
        Jwts.builder().let {
            val now = Date()

            val userPrincipal = authentication?.principal as UserPrincipal

            it.setClaims(
                Jwts.claims().setSubject(userPrincipal.username)
                    .also { claims ->
                        claims["role"] = userPrincipal.authorities.first()
                    }
            )
                .setIssuedAt(now)
                .setExpiration(Date(now.time + TOKEN_TTL))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
                .compact()
        }!!

    fun getAuthentication(token: String?): Authentication =
        userDetailService.loadUserByUsername(this.getUserId(token)).let {
            UsernamePasswordAuthenticationToken(it, it.password, it.authorities)
        }

    // 사용자 Id 가져오기
    fun getUserId(token: String?): String =
        Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject

    // 요청으로부터 토큰 가져오기
    fun resolveToken(req: HttpServletRequest) =
        req.getHeader("Authorization")?.let {
            when (it.startsWith("Bearer ")) {
                true -> it.substring(7, it.length)
                false -> null
            }
        }

    // 토큰 유효성 검사
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .let { !it.body.expiration.before(Date()) }
        } catch (e: Exception) {
            false
        }
    }
}