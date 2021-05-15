package com.shopping.backend.entity

import com.shopping.backend.domain.account.RoleType
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (
    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    var id: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "email")
    var email: String?,
    @Column(name = "password")
    val password: String,
    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    val roleType: RoleType
)