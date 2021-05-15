package com.shopping.backend.controller

import com.shopping.backend.domain.account.RoleType
import com.shopping.backend.domain.account.UserPrincipal
import com.shopping.backend.entity.User
import com.shopping.backend.repository.jpa.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

//    @Autowired
//    lateinit var cacheRedisOps: ReactiveRedisOperations<String, Any>

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

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

    @PutMapping
    fun putMember(user: User): User {
        return SecurityContextHolder.getContext().authentication.principal
            .let { principal ->
                when (principal) {
                    is UserPrincipal -> principal.username
                    else -> throw InternalAuthenticationServiceException("Can not found matched User Principal")
                }.let {
                    val id = it
                    val currentUser = userRepository.findById(id).get()
                    when(currentUser.roleType) {
                        RoleType.ROLE_ADMIN -> {
                            userRepository.save(User(
                                user.id,
                                user.name,
                                user.email,
                                passwordEncoder.encode(user.password),
                                user.roleType
                            ))
                        }
                        else -> {
                            if (user.id == currentUser.id) {
                                userRepository.save(User(
                                    user.id,
                                    user.name,
                                    user.email,
                                    passwordEncoder.encode(user.password),
                                    user.roleType
                                ))
                            } else {
                                throw SecurityException("Can not change diffrent account.")
                            }
                        }
                    }
                }
            }
    }
}