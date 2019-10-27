# Demo Java Polyglot (to JavaScript)


in the root of the compiled sources (bin directory)
jar cfv application-bundle.jar .

java -cp application-bundle.jar nl.amis.java2js.ValidateThroughNPMValidator

and with absolute path to the JS module:
java -cp application-bundle.jar nl.amis.java2js.ValidateThroughNPMValidator /home/developer/eclipse-workspace/my-graal-exploration/bin/validatorbundled.js




To turn the Java application into a native binary executable, we use the following command from the directory that contains the Fat Jar for the application:

For a Java only application:
$GRAALVM_HOME/bin/native-image -cp ./application-bundle.jar -H:Name=joker -H:Class=nl.amis.js2java.Joker -H:+ReportUnsupportedElementsAtRuntime --allow-incomplete-classpath

For a Java plus JavaScript polyglot binary application:

run this from the bin directory

$GRAALVM_HOME/bin/native-image -cp .:./*  --language:js -H:Name=HelloWorld -H:Class=nl.amis.java2js.HelloWorld -H:+ReportUnsupportedElementsAtRuntime --verbose --allow-incomplete-classpath


$GRAALVM_HOME/bin/native-image -cp ./application-bundle.jar  --language:js -H:Name=postalCodeValidator -H:Class=nl.amis.java2js.ValidateThroughNPMValidator --verbose -H:+ReportUnsupportedElementsAtRuntime --allow-incomplete-classpath

To run the native binary, we need to pass the absolute path of the JS module it has to load:
./postalCodeValidator /home/developer/eclipse-workspace/my-graal-exploration/bin/validatorbundled.js

Compare performance:

time ./postalCodeValidator /home/developer/eclipse-workspace/my-graal-exploration/bin/validatorbundled.js
time java -cp application-bundle.jar nl.amis.java2js.ValidateThroughNPMValidator /home/developer/eclipse-workspace/my-graal-exploration/bin/validatorbundled.js


javac ./nl/amis/java2js/JsonPrettyfier.java
native-image --language:js nl.amis.java2js.JsonPrettyfier

(seeL https://www.graalvm.org/docs/reference-manual/native-image/)

# Demo JavaScript to Java

Java Class nl.amis.js2java.Joker is the target for calling from JavaScript. This class returns jokes - either as individual strings or as array or as value in a HashMap. 

To make this class available to the Node application running on GraalVM, we create a JAR file that contains it.

From the root directory of the compiled application classes (the folder that contains the *nl* top level package folder), create the the jar with this command:

jar cfv application-bundle.jar . 

To verify this went well, try running the main method in the Joker class:

java -cp application-bundle.jar nl.amis.js2java.Joker


Next, copy the jar file to root directory for the Node application (the directory) js2java that contains the files joker.js.

mv ../bin/application-bundle.jar .


To run the first demo of a Node application calling out to Java, execute this command:

node --jvm --vm.cp application-bundle.jar joker2.js

This starts up the Node runtime with GraalJS & GraalVM engine with the full Java 8 language poised for action; it makes all resources from application-bundle.jar available in the Java context of the runtime environment; it then executes joker2.js that then gets hold of type Joker and instantiates a Joker object on which it finally invokes a method.

Application joker3.js goes a little beyond this scenario by showing how the JS code can instantiate a Java Array and a Java Map and how a Map and Array can be moved back and forth from JS to Java.

node --jvm --vm.cp application-bundle.jar joker3.js

The JAR application-bundle.jar contains a Java Class ValidateThroughNPMValidator. This class uses GraalVM Polyglot to load and execute an NPM Module called Validator. So invoking methods on this Java Class indirectly means executing JavaScript code. The Node application validateJS2J2JS.js leverages Java Class ValidateThroughNPMValidator - and therefore Node/JS calls Java calls JavaScript. No sweat at all:

node --jvm --vm.cp application-bundle.jar validateJS2J2JS.js

# Notes

To create a self contained JAR file for a directory and all its contents and subdirectories

The basic format of the command for creating a JAR file is:

jar cf jar-file input-file(s)

The options and arguments used in this command are:

The c option indicates that you want to create a JAR file.
The f option indicates that you want the output to go to a file rather than to stdout.
jar-file is the name that you want the resulting JAR file to have. You can use any filename for a JAR file. By convention, JAR filenames are given a .jar extension, though this is not required.
The input-file(s) argument is a space-separated list of one or more files that you want to include in your JAR file. The input-file(s) argument can contain the wildcard * symbol. If any of the "input-files" are directories, the contents of those directories are added to the JAR archive recursively.
v option Produces verbose output on stdout while the JAR file is being built. The verbose output tells you the name of each file as it's added to the JAR file.

jar cfv ../bin/application-bundle.jar . 
mv ../bin/application-bundle.jar .

For example:
java -cp application-bundle.jar nl.amis.java2js.HelloWorld
java -cp application-bundle.jar nl.amis.java2js.ValidateThroughNPMValidator

To create a self contained bundle for a NPM module:

install npx

`npm install -g npx` 

install webpack:

`npm install webpack webpack-cli`

install validator module (example, see https://www.npmjs.com/package/validator )
`npm install validator`

create single bundle for valudator module

`/usr/lib/jvm/graalvm-ce-19.2.1/jre/languages/js/bin/npx  webpack-cli --entry=./node_modules/validator/index.js --output=./validator_bundled.js --output-library-target=this --mode=development`


Argument: output-library-target, Given: "ecs", Choices: "var", "assign", "this", "window", "self", "global", "commonjs", "commonjs2", "commonjs-module", "amd", "umd", "umd2", "jsonp"

not useful: global, window, commonjs, umd, assign
