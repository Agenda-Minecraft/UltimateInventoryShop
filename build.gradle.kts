import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.springframework.boot") version "2.7.4"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "cat.kiwi"
version = "1.1.0"

repositories {
    mavenCentral()
    maven(url="https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url="https://oss.sonatype.org/content/repositories/snapshots")
    maven(url="https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven(url="https://repo.codemc.org/repository/maven-public/")
    maven(url="https://jitpack.io")

}

dependencies {
    // kotlin libs
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // sql utils
    implementation("org.mybatis:mybatis:3.5.11")
    implementation("com.baomidou:mybatis-plus:3.5.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.github.pagehelper:pagehelper:5.3.2")

    // sql drivers
    runtimeOnly("mysql:mysql-connector-java:8.0.30")
    runtimeOnly("org.xerial:sqlite-jdbc:3.39.3.0")
    runtimeOnly("org.postgresql:postgresql:42.5.0")

    // minecraft apis
    implementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("de.tr7zw:item-nbt-api-plugin:2.10.0")
    implementation("com.github.MilkBowl:VaultAPI:1.7.1")
    implementation("me.clip:placeholderapi:2.9.2")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("UltimateInventoryShop")
        dependencies {
            exclude(dependency("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT"))
            exclude(dependency("de.tr7zw:item-nbt-api-plugin:2.10.0"))
            exclude(dependency("com.github.MilkBowl:VaultAPI:1.7.1"))
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