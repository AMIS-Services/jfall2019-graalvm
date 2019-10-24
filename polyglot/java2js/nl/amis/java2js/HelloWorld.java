package nl.amis.java2js;

import org.graalvm.polyglot.*;

public class HelloWorld {
	public static void main(String[] args) {
		Context polyglot = Context.create();
        polyglot.eval("js", "print('Hello JavaScript!')");
        
        Value helloWorldFunction= polyglot.eval("js"
        		               , "(function(name) { return `Hello ${name}, welcome to the world of JavaScript` })");
		// Use the function
		String greeting = helloWorldFunction.execute("John Doe").asString();
		System.out.println(greeting);
		
		// Handle Exception Thrown in JavaScript
		try {
		polyglot.eval("js", "print('Hello JavaScript!'); throw 'I do not feel like executing this';");
		} catch (PolyglotException e) {
            System.out.println("Exception caught from JavaScript Execution. Orginal Exception: "+e.getMessage());
        }       
	}
}
