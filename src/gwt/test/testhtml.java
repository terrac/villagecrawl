package gwt.test;
import gwt.client.game.SoundPlayer;
import gwt.client.main.Game;
import gwt.client.output.HtmlLog;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.rpc.GWTTimer;



public class testhtml{

	
	public static Game setup(Game g) {
		OutputDirector.timer = new GWTTimer();
		//OutputDirector.mpane = new HtmlOut();
		OutputDirector.log = new HtmlLog();
		
		
		OutputDirector.soundPlayer = new SoundPlayer();
		return g;
	}
	static boolean running = true;
}
