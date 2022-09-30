import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.springframework.boot") version "2.7.4"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url="https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url="https://oss.sonatype.org/content/repositories/snapshots")
    maven(url="https://repo.extendedclip.com/content/repositories/placeholderapi")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.mybatis:mybatis:3.5.11")
    implementation("org.mybatis:mybatis-guice:3.17")
    implementation("com.github.pagehelper:pagehelper:5.3.2")
    runtimeOnly("mysql:mysql-connector-java")

    implementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("me.clip:placeholderapi:2.9.2")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("UltimateInventoryShop")
        dependencies {
            exclude(dependency("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT"))
            exclude(dependency("me.clip:placeholderapi:2.9.2"))
        }
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}