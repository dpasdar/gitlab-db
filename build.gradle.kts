
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.41"
    id("org.jetbrains.dokka") version "0.9.18"
}

group = "com.dpasdar"
version = "1.0.0"

repositories {
    jcenter()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    "test"(Test::class) {
        useJUnitPlatform()
    }
    dokka {
        moduleName = ""
        outputFormat = "gfm"
        outputDirectory = "$rootDir/docs"
    }
}

tasks.register<Jar>("uberJar") {
    from(sourceSets.main.get().output)
    dependsOn(configurations.compile)
    from({
        configurations.compile.get().filter{!it.name.startsWith("kotlin")}.map { zipTree(it) }
    })
}

dependencies {
    implementation(kotlin("stdlib"))
    compile("org.gitlab4j:gitlab4j-api:4.12.1")
    compile("org.koin:koin-core:2.0.1")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntime("org.junit.platform:junit-platform-console:1.2.0")

}

publishing {
    publications {
        create<MavenPublication>("default") {
            pom.withXml {
                val dependenciesNode = asNode().appendNode("dependencies")

                //Only the kotlin runtime as transitive
                configurations.implementation.get().dependencies.forEach {
                    val dependencyNode = dependenciesNode.appendNode("dependency")
                    dependencyNode.appendNode("groupId", it.group)
                    dependencyNode.appendNode("artifactId", it.name)
                    dependencyNode.appendNode("version", it.version)
                    dependencyNode.appendNode("scope", "runtime")
                }
            }
            artifact(tasks["uberJar"])
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }

}