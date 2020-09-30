FROM java:openjdk-8-jdk

WORKDIR /app

# Copying all gradle files necessary to install gradle with gradlew
COPY gradle gradle
COPY \
  ./gradle \
  build.gradle \
  gradlew \
  settings.gradle \
  ./

# Install the gradle version used in the repository through gradlew
RUN ./gradlew

# Run gradle assemble to install dependencies before adding the whole repository
RUN ./gradlew compileJava
RUN ./gradlew compileTestJava

ADD . ./
