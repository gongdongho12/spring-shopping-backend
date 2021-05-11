package com.shopping.backend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "category")
data class Category (
    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    var id: Long,
    @Column(name = "category")
    val name: String,
    @Column(name = "description")
    val description: String? = "",
    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)