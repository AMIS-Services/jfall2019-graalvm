package nl.amis.java2js;

import java.io.File;
import java.io.IOException;
import org.graalvm.polyglot.*;

public class LoadAndRunJS {

	public void doIt() {
		Context c = Context.create("js");
		try {
			File calculatorJS = new File(getClass().getClassLoader().getResource("calculator.js").getFile());
			c.eval(Source.newBuilder("js", calculatorJS).build());
			Value fibonacciFunction = c.getBindings("js").getMember("fibonacci");
			Integer fibonacciResult = fibonacciFunction.execute(12).asInt();
			System.out.println("Calculation Result for Fibonacci (12) " + fibonacciResult);
			Value sqrtFunction = c.getBindings("js").getMember("squareRoot");
			Double sqrtResult = sqrtFunction.execute(42).asDouble();
			System.out.println("Calculation Result for Square Root (42) " + sqrtResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoadAndRunJS().doIt();
	}

}
