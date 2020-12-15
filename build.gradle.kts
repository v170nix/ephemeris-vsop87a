import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.publish.maven.MavenPom
import java.net.URI

import org.gradle.api.publish.maven.MavenPublication

plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "5.2.0"
//    id("java")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
}

group = "net.arwix.astronomy2"
val artifactID = "ephemeris-vsop87a"
version = "0.8.7-b"

//setProperty("targetCompatibility", JavaVersion.VERSION_1_6)
//setProperty("sourceCompatibility", JavaVersion.VERSION_1_6)

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = URI("http://dl.bintray.com/v170nix/astronomy2")
    }
}

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    baseName = artifactID
    classifier = "sources"
    dependsOn("classes")
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))
        exclude(dependency("org.jetbrains:annotations"))
        exclude(dependency("org.apiguardian:apiguardian-api"))
        exclude(dependency("net.arwix.astronomy2:astronomy-core"))
        exclude(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core"))
        exclude(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-common"))
        exclude(dependency("org.jetbrains.kotlinx:atomicfu-common"))
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(kotlin("stdlib"))
    implementation("net.arwix.astronomy2:astronomy-core:0.8.7-b")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.2.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType(GradleBuild::class.java) {
        dependsOn(shadowJar)
    }
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${shadowJar.get().archiveName}.pom")
    }
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

bintray {
    user = "v170nix"
    key = findProperty("bintrayApiKey") as String
    setPublications("ProjectPublication")
    publish = true
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "astronomy2"
        name = "ephemeris-vsop87a"
        userOrg = user
//        websiteUrl = "https://blog.simon-wirtz.de"
//        githubRepo = "s1monw1/TlsLibrary"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/v170nix/astronomy2-core.git"
//        version(delegateClosureOf<BintrayExtension.VersionConfig> {
//            name = project.version as String
//        })
    })
}



publishing {
    (publications) {
        register("ProjectPublication", (MavenPublication::class)) {
            from(components.getByName("java"))
            groupId = project.group as String
            artifactId = artifactID
            artifact(shadowJar)
            version = project.version as String
//            pom.addDependencies()
        }
    }
}

fun MavenPom.addDependencies() = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        configurations.compile.allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}
