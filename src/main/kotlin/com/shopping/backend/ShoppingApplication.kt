package com.shopping.backend

import com.shopping.backend.configure.CustomBeanNameGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = ["com.shopping.backend.repository.reactive"])
@EnableJpaRepositories(basePackages = ["com.shopping.backend.repository.jpa"])
@ComponentScan(nameGenerator = CustomBeanNameGenerator::class)
class ShoppingApplication: SpringBootServletInitializer() {

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(ShoppingApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<ShoppingApplication>(*args)
}
