import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "com.sky.account.manager"
version = "3.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("log4j:log4j:1.2.17")
    implementation("org.xerial:sqlite-jdbc:3.36.0.2")
    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        javaHome = "/home/sky/Software/Java/jdk-16" // System.getenv("JDK_15")
        mainClass = "com.sky.account.manager.MainKt"
        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            packageName = "PolarBear"
            packageVersion = "3.0.0"
            description = "Compose PolarBear App"
            copyright = "© 2022 Sky. All rights reserved."
            licenseFile.set(project.file("LICENSE.txt"))

            linux {
                version = "3.0.0"
                iconFile.set(project.file("icon.png"))
            }
        }
    }
}