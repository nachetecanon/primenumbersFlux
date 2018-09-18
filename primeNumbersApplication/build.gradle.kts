import org.jetbrains.kotlin.js.inline.util.extractImportTag

plugins {
    java
    application
    id("org.springframework.boot") version "2.0.4.RELEASE"
    id("org.asciidoctor.convert") version "1.5.3"
    id("com.avast.gradle.docker-compose") version "0.8.7"
}

apply(plugin = "application")
apply(plugin = "docker-compose")
apply(plugin = "org.asciidoctor.convert")

val snippetsDir = file("build/generated-snippets")
val springrestdocs = "2.0.2.RELEASE"

project.description = "P1 project - Application"

tasks {
    "bootJar"(BootJar::class) {
        archiveName = "app.jar"
        mainClassName = "com.example.gradle.p1.DemoApplication"
        applicationName = "primeNumbersApplication"
    }
}


dependencies {
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("org.springframework.boot:spring-boot-actuator")
    compile("org.springframework.boot:spring-boot-devtools")
    compile(":primeNumbersService:")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.projectreactor:reactor-test")
    testCompile("org.spockframework:spock-core:1.0-groovy-2.4")
    testCompile("org.codehaus.groovy:groovy-all:2.4.15")
    testCompile("org.springframework.restdocs:spring-restdocs-webtestclient:${springrestdocs}")
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor:${springrestdocs}")
}

test {
    outputs.dir snippetsDir
}

asciidoctor {
    dependsOn test
            backends "html5"
}


jar {
    dependsOn asciidoctor
}

distTar {
    version "${version}"
    setExcludes(["html5"])
    from asciidoctor
}

bootDistTar {
    version "${version}"
    setExcludes(["html5"])
    from asciidoctor
}


dockerCompose {
    environment.put "DIST_FILE", "${project.name}-boot-${projectVersion}.tar"
    environment.put "DIST_FILE2", "${project.name}"
    environment.put "DIST_VERSION", "${projectVersion}"
    projectName "server"
}

composeBuild {
    dependsOn bootDistTar
}


bootStartScripts {
    doLast {
        unixScript.text = unixScript.text.replace("DEFAULT_JVM_OPTS=""", "DEFAULT_JVM_OPTS="-XshowSettings:vm-Djava.security.egd = file:///dev/./urandom -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5 -XX:InitiatingHeapOccupancyPercent=70 -XX:MaxRAMFraction=2"")
    }
}


