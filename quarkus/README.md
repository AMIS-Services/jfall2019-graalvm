# Building Quarkus APPs with Maven

## Create a new project

With Maven, you can scaffold a new project with:
```
mvn io.quarkus:quarkus-maven-plugin:0.19.1:create \
    -DprojectGroupId=jfall \
    -DprojectArtifactId=quarkus-demo \
    -DprojectVersion=1.0-SNAPSHOT \
    -DclassName="jfall.quarkus-demo.HelloResource"
```
If you decide to generate a REST resource (using the className attribute), the endpoint is exposed at: ```http://localhost:8080/$path```. If you use the default path, the URL is: <http://localhost:8080/hello>.

>The project is either generated in the current directory or in a directory named after the passed artifactId. If the current directory is empty, the project is generated in-place.

> A pair of Dockerfiles for native and jvm mode are also generated in src/main/docker. Instructions to build the image and run the container are written in those Dockerfiles.

# Quarkus Development Mode
```
mvn compile quarkus:dev
```
> `quarkus:dev` runs Quarkus in development mode. This enables hot deployment with background compilation, which means that when you modify your Java files and/or your resource files and refresh your browser, these changes will automatically take effect. This works too for resource files like the configuration property file. Refreshing the browser triggers a scan of the workspace, and if any changes are detected, the Java files are recompiled and the application is redeployed; your request is then serviced by the redeployed application. If there are any issues with compilation or deployment an error page will let you know.

> This will also listen for a debugger on port `5005`. If you want to wait for the debugger to attach before running you can pass `-Ddebug` on the command line. If you don’t want the debugger at all you can use `-Ddebug=false`.

# Packaging and run Quarkus application
The application is packaged using `mvn package`. It produces 2 jar files in `/target`:

- `getting-started-1.0-SNAPSHOT.jar` - containing just the classes and resources of the projects, it’s the regular artifact produced by the Maven build;

- `getting-started-1.0-SNAPSHOT-runner.jar` - being an executable jar. Be aware that it’s not an über-jar as the dependencies are copied into the `target/lib` directory.

You can run the application using: `java -jar target/quarkus-demo-1.0-SNAPSHOT-runner.jar`

# Quarkus - build native image
Create a native executable using: `mvn package -Pnative`.

> In addition to the regular files, the build also produces `target/getting-started-1.0-SNAPSHOT-runner`. You can run it using: `./target/getting-started-1.0-SNAPSHOT-runner`

# Creating a container
You can run the application in a container using the JAR produced by the Quarkus Maven Plugin. 
```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-demo/quarkus-jvm .
docker run -i --rm -p 8080:8080 --name quarkus-jvm quarkus-demo/quarkus-jvm:latest
```

By default, the native executable is tailored for your operating system (Linux, macOS, Windows etc). Because the container may not use the same executable format as the one produced by your operating system, we will instruct the Maven build to produce an executable from inside a container:
```
mvn package -Pnative -Dnative-image.docker-build=true

docker build -f src/main/docker/Dockerfile.native -t quarkus-demo/quarkus-native .
docker run -i --rm -p 8081:8080 --name quarkus-native quarkus-demo/quarkus-native:latest
```
### Compering the memory usage
```
docker stats
CONTAINER ID        NAME                CPU %               MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O           PIDS
20415d601095        quarkus-native      0.03%               4.66MiB / 15.22GiB    0.03%               5.55kB / 0B         8.19kB / 0B         20
b0daf7c952d2        quarkus-jvm         0.10%               118.6MiB / 15.22GiB   0.76%               12.4kB / 496B       60.7MB / 0B         40
```
