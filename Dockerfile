# ERSTER SCHRITT
FROM gradle:6.2.2-jdk11 AS erster_schritt
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle shadowJar --no-daemon


# ZWEITE SCHRITT
# https://hub.docker.com/layers/adoptopenjdk/openjdk11/x86_64-alpine-jre-11.0.6_10/images/sha256-01d563f3e201583b56c5e827a25bc5510fc7fca3754ac003eace7d7cd24be164?context=explore
# Java Version: Adopt OpenJDK 11 in Alpine Linux
FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.6_10

WORKDIR /home
# kopiere die Jar Datei in das Kontainer unter /home/*.jar
COPY --from=erster_schritt /home/gradle/project/build/libs/project_dictionary_javalin-1.0.jar /dictionary.jar

# Der Kontainer
EXPOSE 7070

ENTRYPOINT ["java", "-jar", "/dictionary.jar"]
