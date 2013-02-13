package gwt.client.game;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

public class PlaySound extends VParams {

	
	public PlaySound() {
		// TODO Auto-generated constructor stub
	}
	
	public PlaySound(String sound) {
		super();
		put(VConstants.sound,sound);
	}
	public PlaySound(String sound,boolean music) {
		this(sound);
		put("music",music);
	}
	@Override
	public void execute(Map<String, Object> map) {

		String sound = getS(VConstants.sound);
		if(getB("music")){
			OutputDirector.soundPlayer.playMusic(sound);
			return;
		}
		OutputDirector.soundPlayer.playOnce(sound);
	}
	
	@Override
	public PBase clone() {
		return new PlaySound().copyProperties(this);
	}
}
