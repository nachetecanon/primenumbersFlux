group = "com.valverde.nacho"
version = "1.0.0.RELEASE"

buildscript {
    repositories {
        jcenter()

    }
    dependencies {
        classpath("io.spring.gradle:dependency-management-plugin:1.0.6.RELEASE")
    }
}



subprojects {
plugins {
apply(plugin = "java")
apply(plugin = "pmd")
apply(plugin = "checkstyle")
apply(plugin = "jacoco")
apply(plugin = "io.spring.dependency-management")
}

repositories {
    mavenCentral()
    listOf("https://repo.spring.io/libs-release",
"https://repo.spring.io/libs-snapshot").forEach {
maven {
url = uri(it)
}
}
}

dependencyManagement {
imports {
mavenBom 'io.spring.platform:platform-bom:Cairo-SR3'
}
}

sourceCompatibility = '1.10'
targetCompatibility = '1.10'


checkstyleMain {
this.version = "8.11"
ignoreFailures = false
configFile = rootProject.file("config/java/checkstyle/checkstyle.xml")
}
checkstyleTest {
this.version = "8.11"
ignoreFailures = false
configFile = rootProject.file("config/java/checkstyle/checkstyle.xml")
}

jacoco {
toolVersion = "0.8.1"
}

jacocoTestCoverageVerification {
dependsOn(jacocoTestReport)
violationRules {
failOnViolation = true
rule {
enabled = true
element = 'CLASS'
excludes = ['*Exception', '*Application']

limit {
counter = 'LINE'
value = 'COVEREDRATIO'
minimum = 0.95
}
limit {
counter = 'BRANCH'
value = 'COVEREDRATIO'
minimum = 0.70
}
}
}
}
jacocoTestReport {
reports {
xml.enabled = false
html.enabled = true
}
}

pmdMain {
this.version = '4.10'
ignoreFailures = false
ruleSetConfig = rootProject.resources.text.fromFile("config/java/pmd/ruleset.xml")
reports {
xml.enabled = false
html.enabled = true
}
}

check.dependsOn(checkstyleMain, pmdMain, jacocoTestCoverageVerification)
configure(subprojects.findAll()) {
test {
onlyIf {
!(project.name = ~ / . * -test.* /)
}
}
}


}

