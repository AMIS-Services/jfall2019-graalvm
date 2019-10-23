package nl.amis.java2js;

import org.graalvm.polyglot.Context;

public class CallbackFromJS2J {

	public void doIt() {
		Context c = Context.create("js");
		c.getBindings("js").putMember("friend", new FriendlyNeighbour());
		c.eval("js", "print(`Live from JavaScript: ${friend.goodMorning('Jim')}`)");
	}

	public static void main(String[] args) {
		new CallbackFromJS2J().doIt();
	}

}
