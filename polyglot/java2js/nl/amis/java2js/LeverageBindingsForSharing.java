package nl.amis.java2js;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.Source.Builder;
import org.graalvm.polyglot.proxy.ProxyObject;

public class LeverageBindingsForSharing {

	public void doIt() {
		Context c = Context.create("js");
		try {
//			File countriesJS = new File(getClass().getClassLoader().getResource("countries.js").getFile());
//			c.eval(Source.newBuilder("js", countriesJS).build());
//			System.out.println("Current contents of Bindings object: " + c.getBindings("js").getMemberKeys());
//			// get the value of a constant defined in JavaScript from the bindings map
//			Value theAnswer = c.getBindings("js").getMember("answerToLifeUniverseAndEverything");
//			System.out.println("The Answer to Life, Universe and Everything " + theAnswer);

			// create a Map and store it as JavaScript object (that is what ProxyObject
			// fromMap is for) in the bindings object
			Map<String, Object> backingMap = new HashMap<>();
			backingMap.put("myKey", "myValue");
			backingMap.put("myQuestion", "2*3");
			c.getBindings("js").putMember("hostObject", ProxyObject.fromMap(backingMap));
			// access the Java Map turned JavaScript object in bindings from JavaScript:
			Integer answer = c.eval("js", "print(`your key = ${hostObject.myKey}`);"
					+ "hostObject.yourAnswer = eval(hostObject.myQuestion) ; eval(hostObject.yourAnswer)")
					.asInt();
			// the answer is available from the evaluation of the JS snippet
			System.out.println("The Answer to " + backingMap.get("myQuestion") + " = " + answer);
			// and also from the updated hostObject/backingMap in the bindings map
			System.out.println("The Answer to " + backingMap.get("myQuestion") + " = " + backingMap.get("yourAnswer"));
			// creating new objects in JavaScript adds them to the bindings object - and
			// makes them accessible in Java
			c.eval("js", "var PI = 3.141592");
			System.out.println("Current contents of Bindings object: " + c.getBindings("js").getMemberKeys());
			Double pi = c.getBindings("js").getMember("PI").asDouble();
			System.out.println("PI according to JavaScript = " + pi);

			Map atlas = c.getBindings("js").getMember("atlas").as(java.util.Map.class);
			System.out.println("Message from JavaScript Object: " + atlas.get("message"));

			// get hold of JavaScript Array Object and iterate through its constituent
			// elements

			File countriesJS = new File(getClass().getClassLoader().getResource("countries.js").getFile());
			c.eval(Source.newBuilder("js", countriesJS).build());
			Value value = c.getBindings("js").getMember("countries");
			if (value.hasArrayElements()) {
				for (int i = 0; i < value.getArraySize(); i++) {
					Map country = value.getArrayElement(i).as(java.util.Map.class);
					System.out.println(country.get("name"));
				}
			}

			// leverage JSON interpretation in JavaScript
			Value jsonMapper = c.eval("js",
					"(function(jsonString) { " + "var jsonMap= JSON.parse(jsonString);return jsonMap})");
			// turn String containing JSON into true Java Map object
			String jsonString = "{\"name\":\"John\",\"age\" :42,\"city\" :\"Zoetermeer\"}";
			Map jsonMap = jsonMapper.execute(jsonString).as(java.util.Map.class);
			System.out.println(jsonMap.keySet());
			System.out.println("The age of " + jsonMap.get("name") + " is " + jsonMap.get("age"));

			// load JSON data from file, use JS to evaluate
			File employeesJSONFile = new File(getClass().getClassLoader().getResource("employees.json").getFile());
			String employeesJSON = LeverageBindingsForSharing.readFile(employeesJSONFile.toPath(),
					StandardCharsets.UTF_8);
			// leverage JS to create an object called employees from the contents of the JSON file
			c.eval("js", "var employees = " + employeesJSON.toString());
			// get the array of employees from the JSON document (that has been turned into a JS object)
			Value array = c.eval("js", "employees.Employees");
			// get the second employee from the original JSON document
			for (int i = 0; i < array.getArraySize(); i++) {
		        Map emp = array.getArrayElement(i).as(java.util.Map.class);
				System.out.println(emp.get("firstName")+" in job " + emp.get("jobTitleName"));			
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String readFile(Path path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	}

	public static void main(String[] args) {
		new LeverageBindingsForSharing().doIt();
	}

}
