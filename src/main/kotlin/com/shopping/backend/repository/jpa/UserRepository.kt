package com.shopping.backend.repository.jpa

import org.springframework.stereotype.Repository
import com.shopping.backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface UserRepository: JpaRepository<User, String> {
}