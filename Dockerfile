FROM ubuntu:latest

# Install Oracle Java
RUN apt-get update
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN apt-get install -y wget && apt-get install -y curl
RUN mkdir /usr/lib/java
WORKDIR /usr/lib/java
RUN wget https://download.oracle.com/java/19/archive/jdk-19.0.2_linux-x64_bin.tar.gz
RUN tar zxvf jdk-19.0.2_linux-x64_bin.tar.gz
RUN rm *.tar.gz
RUN update-alternatives --install "/usr/bin/java" "java" "/usr/lib/java/jdk-19.0.2/bin/java" 1
RUN apt-get install -y libsqlite3-dev
RUN apt-get install -y maven

# Copy your Spring application to the container
COPY . /usr/src/groundschool
WORKDIR /usr/src/groundschool

# Your databases should be in the new folder
RUN chmod 755 src/databases/*.db

# Make sure you create this file as a copy of application.yaml
RUN mv hidden_application.yaml src/main/resources/application.yaml

# Build the application
RUN mvn clean install -e -X

# Start the application
CMD ["java", "-jar", "target/groundschool-0.0.1-SNAPSHOT.jar"]
