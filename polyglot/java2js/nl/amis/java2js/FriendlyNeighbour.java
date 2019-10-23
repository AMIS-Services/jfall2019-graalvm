package nl.amis.java2js;

import org.graalvm.polyglot.HostAccess;

public class FriendlyNeighbour {
	@HostAccess.Export
	public String goodMorning(String myName) {
		return "Good morning my dear " + myName
				+ " from the wonderful world of Java";	
	}
}
