package com.shopping.backend.controller

import com.shopping.backend.domain.account.UserPrincipal
import com.shopping.backend.entity.User
import com.shopping.backend.repository.jpa.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var cacheRedisOps: ReactiveRedisOperations<String, Any>

    @GetMapping
    fun getUser(): () -> Optional<User> =
        // Spring security 로부터 Principal을 가져옵니다.
        // 인증 필터에서 user 정보가 이미 세팅되었습니다.
        SecurityContextHolder.getContext().authentication.principal
            .let { principal ->
                when(principal) {
                    // 객체 타입이 UserPrincial인면 username을 받아옵니다.
                    is UserPrincipal -> principal.username
                    else -> throw InternalAuthenticationServiceException("Can not found matched User Principal")
                }.let { id -> {
                    userRepository.findById(id)
                }
            }}
}