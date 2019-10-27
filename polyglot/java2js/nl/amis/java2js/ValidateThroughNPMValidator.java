package nl.amis.java2js;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.graalvm.polyglot.*;

public class ValidateThroughNPMValidator {

	private Context c;

	public ValidateThroughNPMValidator() {
		try {
			// load file validatorbundled.js from root of Java package structure aka root of
			// JAR archive file
			InputStream is = getClass().getResourceAsStream("/validatorbundled.js");
			readAndEvaluateJavaScriptSource(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ValidateThroughNPMValidator(String validatorBundleSourceFile) {
		try {
			// load file validatorbundled.js from root of Java package structure aka root of
			// JAR archive file
			System.out.println("Loading Validator Module from " + validatorBundleSourceFile);
			InputStream is = new FileInputStream(validatorBundleSourceFile);
			readAndEvaluateJavaScriptSource(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readAndEvaluateJavaScriptSource(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader JSreader = new BufferedReader(isr);

		// create Polyglot Context for JavaScript and load NPM module validator (bundled
		// as self contained resource)
		c = Context.create("js");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		// load output from WebPack for Validator Module - a single bundled JS file
		c.eval(Source.newBuilder("js", JSreader, "validatorbundled").build());
		System.out.println("All functions available from Java (as loaded into Bindings) "
				+ c.getBindings("js").getMemberKeys());
		JSreader.close();
		isr.close();
	}
	
	public Boolean isPostalCode(String postalCodeToValidate, String country) {
		// use validation function isPostalCode(str, locale) from NPM Validator Module
		// to validate postal code
		Value postalCodeValidator = c.getBindings("js").getMember("isPostalCode");
		Boolean postalCodeValidationResult = postalCodeValidator.execute(postalCodeToValidate, country).asBoolean();
		return postalCodeValidationResult;
	}

	// pass 
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++) {
            System.out.println("Args "+i+": "+args[i]);
        }
		// if the filename was passed as startup argument, then load the validator bundle according to that specification
		ValidateThroughNPMValidator v = args.length>0?new ValidateThroughNPMValidator(args[0]):new ValidateThroughNPMValidator();
		System.out.println("Postal Code Validation Result " + v.isPostalCode("3214 TT", "NL"));
		System.out.println("Postal Code Validation Result " + v.isPostalCode("XX 27165", "NL"));
	}

}
