# Running Camel with Quarkus

##Create a new project

With Maven, you can scaffold a new project with:
```
mvn io.quarkus:quarkus-maven-plugin:0.19.1:create \
    -DprojectGroupId=jfall \
    -DprojectArtifactId=quarkus-camel \
    -DprojectVersion=1.0-SNAPSHOT 
```

Now that we have our Quarkus app created. Let's try to add Camel to the mix to enable reusable integration patterns and connectors in our projects. 
> Quarkus already offers a variety extensions and Camel is one of those. At the time of writing there are just a few extensions available. We won't get the whole set of 200+ Camel components compiled to native executable packages, but I'm quite sure that the Quarkus ecosystem will grow quickly.

##Adding Camel extension
Let's list the available extensions.\
```mvn quarkus:list-extensions```

Then let's add the camel extension to it.

```mvn quarkus:add-extension -Dextensions=io.quarkus:quarkus-camel-core```

As a result you should see the dependency to the quarkus camel extension being added to the pom file
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-camel-core</artifactId>
</dependency>
```
Lets create a simple class containing a Camel route to see if things work. Add a new class called CamelRouteBuilder to the source folder. With the following content.

```java
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder {

    public void configure() {
        from("timer://testTimer").log("test");
    }
}
```
##Running Camel
Now we can run our project with

`mvn compile quarkus:dev`
```jshelllanguage
2019-10-19 17:26:00,107 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Route: route1 started and consuming from: timer://testTimer
2019-10-19 17:26:00,108 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Total 1 routes, of which 1 are started
2019-10-19 17:26:00,109 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Apache Camel 3.0.0-M2 (CamelContext: camel-1) started in 0.011 seconds
2019-10-19 17:26:00,179 INFO  [io.quarkus] (main) Quarkus 0.19.1 started in 1.440s. Listening on: http://[::]:8080
2019-10-19 17:26:00,181 INFO  [io.quarkus] (main) Installed features: [camel-core, cdi, resteasy]
2019-10-19 17:26:01,119 INFO  [route1] (Camel (camel-1) thread #1 - timer://testTimer) test
```
We can note that the boot time is a bit higher when including camel, it is now 1.440s.

Let's compile it to a native executable and see how it behaves.

`mvn package -Pnative`
`./target/quarkus-camel-1.0-SNAPSHOT-runner`
```jshelllanguage
2019-10-19 17:41:07,798 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Route: route1 started and consuming from: timer://testTimer
2019-10-19 17:41:07,799 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Total 1 routes, of which 1 are started
2019-10-19 17:41:07,799 INFO  [io.qua.cam.cor.run.sup.FastCamelContext] (main) Apache Camel 3.0.0-M2 (CamelContext: camel-1) started in 0.002 seconds
2019-10-19 17:41:07,806 INFO  [io.quarkus] (main) Quarkus 0.19.1 started in 0.014s. Listening on: http://[::]:8080
2019-10-19 17:41:07,806 INFO  [io.quarkus] (main) Installed features: [camel-core, cdi, resteasy]
2019-10-19 17:41:08,799 INFO  [route1] (Camel (camel-1) thread #0 - timer://testTimer) test
```
We can see that the boot time is of 14 milliseconds!