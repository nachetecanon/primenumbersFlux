
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("io.spring.gradle:dependency-management-plugin:1.0.6.RELEASE")
    }

}

plugins {
    "kotlin-dsl"
}

object Versioning {
    val springBootVersion="2.0.4.RELEASE"
    val groupId="com.valverde.nacho"
    val projectVersion="1.0.1-SNAPSHOT"
}
object Config {
    object Versioning
}