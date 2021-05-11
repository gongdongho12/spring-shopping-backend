package com.shopping.backend.repository.reactive

import org.springframework.stereotype.Repository
import com.shopping.backend.entity.User
import org.springframework.data.r2dbc.repository.R2dbcRepository

@Repository
interface UserRxRepository: R2dbcRepository<User, String> {
}