package gwt.client.output;

import gwt.client.game.SoundPlayer;
import gwt.client.main.Timer;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.AreaMap;
import gwt.client.map.SymbolicMap;

public class OutputDirector {
	public static Timer timer =null;
	public static MainPanel mpane;
	public static HtmlOut getHtmlOut(){
		return (HtmlOut)mpane;
	}
	public static SoundPlayer soundPlayer = null;
	
	public static boolean running = true;
	public static ILog log;
	public static EUtil util;
	public static void log(String type,String message){
		if(log != null){
			log.log(type, message);
		}
	}
	public static void run() {
		running = false;
		if(log != null){
			log.run();
		}
		
	}
	
//	public static void displayAreaMap(LivingBeing per) {
//		mpane.displayAreaMap(per);
//	}
//	public static void displaySymbolicMap(SymbolicMap map) {
//		mpane.displaySymbolicMap(map);
//	}
//	
//
//	public static void refreshDisplays(){
//		mpane.refreshFmds();
//		
//	}

}
