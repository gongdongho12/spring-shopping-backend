import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    war
    java
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.4.31"
}

group = "com.shopping.backend"
version = "${System.currentTimeMillis()}-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8
val swaggerVersion = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-web") {
//        exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
//    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.springframework.plugin:spring-plugin-core:2.0.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("io.springfox:springfox-boot-starter:${swaggerVersion}") {
        // spring-boot-starter-data-rest의 버전이 호환되지 않기에 exclude 처리
        exclude("org.springframework.boot", "spring-boot-starter-data-rest")
    }
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.mariadb:r2dbc-mariadb:1.0.1")
    implementation("mysql:mysql-connector-java")
//    implementation("org.jboss.logging:jboss-logging:3.4.1.Final")
//    implementation("org.jboss.slf4j:slf4j-jboss-logmanager:1.1.0.Final")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

//configurations {
//    all {
//        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
//        exclude(group = "org.springframework.boot", module = "logback-classic")
//        exclude(group = "org.slf4j", module = "slf4j-log4j12")
//    }
//}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<War> {
    enabled = true
}

tasks.withType<Jar> {
    enabled = true
}