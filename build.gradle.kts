plugins {
    kotlin("jvm") version "2.1.0"
}

group = "me.mark"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}