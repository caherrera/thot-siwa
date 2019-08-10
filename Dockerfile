# We use a image with a prebuilt java 8 software
FROM java:8

# Update the repository and install maven and nodejs
RUN apt-get update
RUN curl -sL https://deb.nodesource.com/setup_10.x | bash -
RUN apt-get install -y maven nodejs

# Install Apidoc
RUN npm install apidoc -g

# The siwa like a directory
WORKDIR /siwa

# Add the pom and src image of the app and create the doc folder
ADD pom.xml /siwa/pom.xml
ADD src /siwa/src
RUN mkdir -p src/main/resources/public

# Run the apidoc to make the api documentation
RUN ["apidoc", "-o", "src/main/resources/public/api"]

# Select JAVA 8 like the default java in the container and package the app
RUN ["update-java-alternatives", "-s", "java-1.8.0-openjdk-amd64"]
RUN ["mvn", "clean", "package"]

# Expose port 4576 to access to the app from outside
EXPOSE 4576

# Run the app after the mvn package
CMD ["java", "-jar", "target/siwa-jar-with-dependencies.jar"]