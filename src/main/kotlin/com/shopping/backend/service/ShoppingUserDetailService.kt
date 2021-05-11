package com.shopping.backend.service

import com.shopping.backend.domain.account.UserPrincipal
import com.shopping.backend.repository.jpa.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ShoppingUserDetailService: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(id: String?): UserDetails {
        return id?.let {
            userRepository.findById(it).let {
                it.get()?.let {
                    UserPrincipal(
                        it.id,
                        it.name,
                        it.password,
                        listOf<GrantedAuthority>(SimpleGrantedAuthority(it.roleType.name))
                    )
                }
            }
        } ?: throw UsernameNotFoundException("Can not found account.")
    }
}
