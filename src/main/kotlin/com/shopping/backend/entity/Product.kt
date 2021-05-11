package com.shopping.backend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product")
data class Product (
    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    var id: Long,
    @Column(name = "name")
    val name: String,
    @Column(name = "price")
    val price: Long,
    @Column(name = "image")
    val image: String,
//    @ManyToOne
//    @JoinColumn(foreignKey = ForeignKey(name = "fk_category"))
//    val category: Category?,
//    @ManyToOne
//    @JoinColumn(foreignKey = ForeignKey(name = "fk_seller"))
//    val seller: User?,
    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)