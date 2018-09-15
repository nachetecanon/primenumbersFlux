# primenumbersFlux
Exercise based in searching prime numbers, but most of all exposing an Api via Spring-webflux and managing the source with Gradle.

There will be a number of transitions:
 * from maven-java project to gradle-java project in this first version.
 * from gradle-groovy to gradle-kotling.
 * from java as a language programming to kotkling
 * change testing tools

## External references used at this project

I'm sure I miss something, but these are some of the references I've being using to develop this exercise.

* [Small ubuntu image](https://github.com/bitnami/minideb) : specially useful avoiding some typical _libgc_ problems of alpine distributiions
 and also getting a little enough base image.
* [Documenting api rest from spring docs](https://spring.io/guides/gs/testing-restdocs/)
* [Documenting api rest from Baeldung blog](https://www.baeldung.com/spring-rest-docs)
* [Spring boot Web-Flux](https://www.baeldung.com/spring-webflux) along with official docs from spring
* [Gradle docker compose plugin](https://github.com/avast/gradle-docker-compose-plugin)

## Use

Use this command to generate the distribution spring-boot tar

```
gradle clean build

```

If you want, you can also generate a docker image this way

```

gradle clean build composeBuild


```

## Api use

Once the application is up (using docker or docker-compose), you can consult the _index.html_ 
file included at the tar distribution file, to know how to invoke it.

## Versions

* 1.0.0.RELEASE: generation of 

## Licenses

[MIT license terms](LICENSE)

