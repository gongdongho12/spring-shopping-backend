package com.shopping.backend.controller

import com.shopping.backend.repository.reactive.ProductRxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    lateinit var productRxRepository: ProductRxRepository

    @Autowired
    lateinit var cacheRedisOps: ReactiveRedisOperations<String, Any>

    @GetMapping
    fun getProdict(@RequestParam("id") id: Long): Mono<Any> {
        val key = "product:${id}"
        return cacheRedisOps.opsForValue().get(key).switchIfEmpty {
            productRxRepository.findById(id).map {
                cacheRedisOps.opsForValue().set(key, it).subscribe()
                it
            }
        }
    }
}