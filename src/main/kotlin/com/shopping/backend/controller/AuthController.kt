package com.shopping.backend.controller

import com.shopping.backend.domain.account.AuthReqModel
import com.shopping.backend.domain.account.RoleType
import com.shopping.backend.entity.User
import com.shopping.backend.provider.JwtTokenProvider
import com.shopping.backend.repository.jpa.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/sign-in")
    fun signIn(@RequestBody authReqModel: AuthReqModel): String =
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authReqModel.id,
                authReqModel.password
            )
        )
            .let { authentication ->
                SecurityContextHolder.getContext().authentication = authentication
                return tokenProvider.createToken(authentication)
            }

    @PostMapping("/sign-up")
    fun signUp(@RequestBody authReqModel: AuthReqModel): ResponseEntity<Any> {
        if (authReqModel.roleType != null && authReqModel.name != null) {
            val password = passwordEncoder.encode(authReqModel.password)
            println("password ${password}")
            return ResponseEntity.ok(
                    userRepository.save(
                        User(
                            authReqModel.id,
                            authReqModel.name,
                            authReqModel.email?: null,
                            password,
                            RoleType.valueOf(authReqModel.roleType)
                        )
                    )
            )
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}