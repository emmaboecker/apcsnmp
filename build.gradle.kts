import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    application
}

group = "net.stckoverflw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.opennms.org/content/groups/opennms.org-release")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("org.soulwing.snmp:tnm4j:1.1.1")
    implementation("com.influxdb:influxdb-client-kotlin:6.0.0")
    implementation("dev.inmo:krontab:0.7.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks {
    jar {
        manifest {
            attributes("Main-Class" to "net.stckoverflw.apcsnmp.LauncherKt")
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

application {
    mainClass.set("net.stckoverflw.apcsnmp.LauncherKt")
}
