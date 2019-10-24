package nl.amis.java2js;

import org.graalvm.polyglot.*;

import nl.amis.java2js.CacheAndCounter;

public class MultiThreadedJS {
	public static void main(String[] args) {
		CacheAndCounter cac = new CacheAndCounter();
		cac.inc();
		cac.put("name", "John Doe");

		String jsCode = "(function(x) {" + "cacheAndCounter.inc(); "
				+ "cacheAndCounter.put('name', keys[Math.floor(Math.random() * 3)]);" + "})";

		Context c2 = Context.create("js");
		c2.eval("js", "keys = ['Donald Duck','Scrooge McDuck','Mickey Mouse'];");
		c2.getBindings("js").putMember("cacheAndCounter", cac);

		Thread thread = new Thread(new Runnable() {
			Value jsFunction = c2.eval("js", jsCode);
			@Override
			public void run() {
				while (cac.getCounter() < 24) {
					synchronized (cac) {
						jsFunction.execute(42).asString();
						System.out.println("Result from second thread - using context 2 "+cac.get("name"));
						}
					try {
						Thread.sleep(Math.round(200 * Math.random()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();

		Context c1 = Context.create("js");
		c1.eval("js", "keys = ['Beyonce','Ariane Grande','Tina Turner'];");
		c1.getBindings("js").putMember("cacheAndCounter", cac);
		int runs = 7;
		Value jsFunctionMain = c1.eval("js", jsCode);
		while (runs-- > 0) {
			synchronized (cac) {
				jsFunctionMain.execute(44);
				System.out.println("Result from main thread - using context 1 "+cac.get("name"));
			}
			try {
				Thread.sleep(Math.round(200 * Math.random()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
ckTrace();
			}
		}
	}
}
