package nl.amis.java2js;

import java.io.File;
import java.io.IOException;
import org.graalvm.polyglot.*;

public class ValidateThroughNPMValidator {

	private Context c;

	public ValidateThroughNPMValidator() {
		// create Polyglot Context for JavaScript and load NPM module validator (bundled as self contained resource)
		c = Context.create("js");
		try {
			// load output from WebPack for Validator Module - a single bundled JS file
			File validatorBundleJS = new File(
					getClass().getClassLoader().getResource("validator_bundled.js").getFile());
			c.eval(Source.newBuilder("js", validatorBundleJS).build());
			System.out.println("All functions available from Java (as loaded into Bindings) "
					+ c.getBindings("js").getMemberKeys());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Boolean isPostalCode(String postalCodeToValidate, String country) {
		// use validation function isPostalCode(str, locale) from NPM Validator Module to validate postal code 
		Value postalCodeValidator = c.getBindings("js").getMember("isPostalCode");
		Boolean postalCodeValidationResult = postalCodeValidator.execute(postalCodeToValidate, country).asBoolean();
		return postalCodeValidationResult;
	}

	public static void main(String[] args) {
		ValidateThroughNPMValidator v = new ValidateThroughNPMValidator();
		System.out.println("Postal Code Validation Result " + v.isPostalCode("3214 TT", "NL"));
		System.out.println("Postal Code Validation Result " + v.isPostalCode("XX 27165", "NL"));
	}

}
