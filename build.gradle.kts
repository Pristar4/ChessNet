import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("edu.sc.seis.launch4j") version "2.5.3"
    id("org.jetbrains.dokka") version "1.7.20"

    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

var mainClass = "MainKt"
var appName = "ChessNet"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Kotlin Documentation tool: Dokka
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

// Magical code to make the jar executable

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.withType<edu.sc.seis.launch4j.tasks.DefaultLaunch4jTask> {
    outfile = "${appName}.exe"
    mainClassName = mainClass
    icon = "$projectDir/icons/myApp.ico"
    productName = "ChessNet"
}

tasks.register<edu.sc.seis.launch4j.tasks.Launch4jLibraryTask>("createFastStart") {
    outfile = "ChessNet.exe"
    mainClassName = "com.example.myapp.FastStart"
    icon = "$projectDir/icons/myAppFast.ico"
    fileDescription = "Faster implementation of the ChessNet engine"
}
tasks.register<edu.sc.seis.launch4j.tasks.Launch4jLibraryTask>("MyApp-memory") {
    fileDescription = "The default implementation with increased heap size"
    maxHeapPercent = 50
}