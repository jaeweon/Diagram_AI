plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("org.jetbrains.intellij") version "1.14.1"
}

group = "com.diagram"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// IntelliJ 플러그인 설정
intellij {
    version.set("2022.2.5")
    type.set("IC") // Community Edition
    // 플러그인 의존성: java, kotlin 등
    plugins.set(listOf("java", "org.jetbrains.kotlin"))
}

dependencies {
    // 라이브러리 의존성 (PlantUML, Lombok, Spring 등)
    implementation("net.sourceforge.plantuml:plantuml:1.2023.8")
    implementation("org.springframework:spring-context:5.3.29")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    runIde {
        jvmArgs = listOf("-Xmx2G")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
