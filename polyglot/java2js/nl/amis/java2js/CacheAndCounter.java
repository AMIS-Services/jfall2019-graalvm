package nl.amis.java2js;

import java.util.HashMap;
import java.util.Map;
import org.graalvm.polyglot.*;


public class CacheAndCounter {
	
	private int value = 10;
	private Map theMap = new HashMap();
	
	public int getCounter() {
		return value;
	}
	
	@HostAccess.Export
	public void inc() {
		value++;
		//System.out.println("...(word from CacheAndCounter: Value is now at: "+value+")");
	}

	@HostAccess.Export
	public void put(String key, Object thing) {
		theMap.put(key, thing);
		//System.out.println("...(word from CacheAndCounter: Object set for "+key+" is "+thing+")");
	}
	
	@HostAccess.Export
	public Object get(String key) {
		return theMap.get(key);
	}
	

}
ect get(String key) {
		return theMap.get(key);
	}
	

}
