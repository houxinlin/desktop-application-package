import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm") version "1.5.10"
    "jar"
}

group = "com.desktop.webpackage"
version = "1.0"

repositories {
    mavenCentral()
}
//compileOptions {
//    sourceCompatibility JavaVersion.VERSION_1_8
//            targetCompatibility JavaVersion.VERSION_1_8
//}

tasks.jar{
    manifest {
        attributes.set("Main-Class", "com.desktop.pack.DesktopMain")
    }

//    configurations.runtimeClasspath.configure {
//        this.files.forEach {
//            println(it)
//        }
//    }



//    from {
//        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
//    }
}
dependencies {

    implementation("com.alibaba:fastjson:1.2.79")

    implementation(files("/home/HouXinLin/project/java/FileMerge/FileMerge/build/libs/FileMerge-1.0-SNAPSHOT.jar"))
    implementation(files("/home/HouXinLin/project/java/desktop-application-definition/build/libs/desktop-application-definition-1.0-SNAPSHOT.jar"))
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

sourceSets {
    main {
        java.srcDir("src/core/java")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    test {
        testLogging.showExceptions = true
    }
}