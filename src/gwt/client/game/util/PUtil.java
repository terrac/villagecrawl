package gwt.client.game.util;

import java.util.Map.Entry;

import com.google.gwt.user.client.Command;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;

public class PUtil {

	public static void add(LivingBeing lb, int damage, String health2) {
		int health = lb.getStats().getInt(health2);
		health = health + damage;
		lb.getStats().put(health2, health);
	}

	public static String pToString(String key,PBase pb) {

		String a =key+ ":";
		for (Entry<String, Object> e : pb.getObjMap().entrySet()) {
			a += "\n" + e.getKey() + ": " + e.getValue();
		}
		return a;

	}
	public static String pToStringLimited(String key,PBase pb) {

		String a =key+ ":";
		for (Entry<String, Object> e : pb.getObjMap().entrySet()) {
			if(e.getValue() instanceof PBase){
				a += "\n" + e.getKey() ;
			}else {
				a += "\n" + e.getKey() + ": " + e.getValue();
			}
			
		}
		return a;

	}
	public static PBase getFromChildren(PBase pbase,String key){
		for (Entry<String, Object> eo : pbase.getObjMap().entrySet()) {
			if (eo.getValue() instanceof PBase) {
				PBase pb = (PBase) ((PBase) eo.getValue()).get(key);
				if(pb != null){
					return pb;
				}
				
			}
		}
		return null;
	}
}
