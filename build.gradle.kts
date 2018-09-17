import org.gradle.internal.jacoco.rules.JacocoViolationRuleImpl

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
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_10
        targetCompatibility = sourceCompatibility
    }


    configure<CheckstyleExtension> {
        toolVersion = "8.11"
        maxErrors = 0
        configFile = rootProject.file("config/java/checkstyle/checkstyle.xml")
    }
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.1"
        evaluationDependsOn("jacocoTestReport")

    }
    configure<JacocoReport> {
        reports {
            html
        }
    }
    configure<JacocoCoverageVerification> {
        dependsOn("JacocoReport")
        violationRules {
            isFailOnViolation = true
            rule {
                enabled = true
                element = "CLASS"
                excludes = listOf("*Exception", "*Application")
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal.valueOf(0.95)
                }
                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal.valueOf(0.70)
                }
            }
        }
    }
    configure<PmdExtension> {
        toolVersion = "4.10"
        isIgnoreFailures = false
        ruleSetConfig = rootProject.resources.text.fromFile("config/java/pmd/ruleset.xml")
    }
    configure<PmdReports> {
        html
    }


}


