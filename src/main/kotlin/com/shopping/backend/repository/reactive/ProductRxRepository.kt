package com.shopping.backend.repository.reactive

import com.shopping.backend.entity.Product
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface ProductRxRepository: R2dbcRepository<Product, Long> {
}