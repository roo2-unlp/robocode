plugins {
    id("net.sf.robocode.java-conventions")
    application
    `java-library`
}

description = "Robocode Main application"

dependencies {
    api(project(":robocode.api"))
    implementation(project(":robocode.core"))
    implementation(project(":robocode.host"))
    implementation(project(":robocode.repository"))
    implementation(project(":robocode.battle"))
    implementation(project(":robocode.ui"))
    implementation(project(":robocode.sound"))
}

tasks {
    publishMavenJavaPublicationToSonatypeRepository {
        enabled = false
    }
    run.configure {
        workingDir = file("build/install/robocode.main")
    }
}

application {
    mainClass.set("net.sf.robocode.main.Robocode")  // Agregamos .main
}