package com.shopping.backend.configure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Value("\${spring.redis.host}")
    val host: String = "127.0.0.1"

    @Value("\${spring.redis.port}")
    val port: Int = 6379

    @Value("\${spring.redis.database}")
    val database: Int = 1

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Primary
    @Bean("primaryRedisConnectionFactory")
    fun connectionFactory(): ReactiveRedisConnectionFactory? {
        val connectionFactory = LettuceConnectionFactory(host, port)
        connectionFactory.database = database
        return connectionFactory
    }

    @Bean
    fun contentReactiveRedisTemplate(factory: ReactiveRedisConnectionFactory?): ReactiveRedisTemplate<String, Any>? {
        val keySerializer = StringRedisSerializer()
        val redisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
            .apply {
                setObjectMapper(
                    jacksonObjectMapper()
                        .registerModule(JavaTimeModule())
                )
            }
        val serializationContext = RedisSerializationContext
            .newSerializationContext<String, Any>()
            .key(keySerializer)
            .hashKey(keySerializer)
            .value(redisSerializer)
            .hashValue(redisSerializer)
            .build()
        return ReactiveRedisTemplate(factory!!, serializationContext)
    }
}