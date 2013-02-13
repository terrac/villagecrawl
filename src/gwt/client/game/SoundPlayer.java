package gwt.client.game;

import java.util.HashMap;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.main.VConstants;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class SoundPlayer {

	
	
	SoundController soundController;
	Map<String,Sound> soundMap = new HashMap();
	{
		soundController = new SoundController();
		
		// }
	}

	protected boolean shouldPlay() {
		return EntryPoint.game.getB(VConstants.sound);
	}
	Sound music;
	String musurl;
	public void playMusic(String musicStr) {
		musurl = "/sounds/" + musicStr + ".mp3";
		if (!shouldPlay()) {
			return;
		}
		
		
		playCurrentMusic();
	}

	public void playCurrentMusic() {
		if(musurl == null){
			return;
		}
		if(soundMap.containsKey(musurl)){
			music=soundMap.get(musurl);
		} else {
			music = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
					musurl);
			soundMap.put(musurl,music);
		}
		
		music.setVolume(25);

		// System.out.println(sound.getLoadState());
		// if(sound.getLoadState().equals(Sound.LoadState.LOAD_STATE_SUPPORTED_AND_READY)){
		music.play();
		SoundHandler handler = new SoundHandler() {

			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {

				music.play();

			}
		};

		music.addEventHandler(handler);
	}
	public void stopMusic(){
		if(music == null){
			return;
		}
		music.stop();
	}
	
	public void playOnce(String ssound) {
		
		Sound soundOnce;
		if (!shouldPlay()) {
			return;
		}
		
		
		String url = "/sounds/" + ssound + ".ogg";
		if(soundMap.containsKey(url)){
			soundOnce = soundMap.get(url);
		} else {
			soundOnce = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,
					url);
			soundMap.put(url,soundOnce);
		}
		
		soundOnce.setVolume(50);
		
		if (soundOnce.getLoadState().equals(
				Sound.LoadState.LOAD_STATE_SUPPORTED_MAYBE_READY)) {
			soundOnce.play();
			soundOnce.play();
		}
		
	}

}
