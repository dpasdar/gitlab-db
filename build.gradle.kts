
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.41"
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
    testImplementation("junit:junit:4.12")
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