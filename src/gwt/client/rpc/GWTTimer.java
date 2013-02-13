package gwt.client.rpc;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.edit.EditPage;
import gwt.client.edit.HtmlEditPage;
import gwt.client.game.util.FlowControlException;
import gwt.client.main.Game;
import gwt.client.main.Timer;
import gwt.client.output.OutputDirector;
import gwt.shared.buildjson;

public class GWTTimer extends Timer {
	
	
	boolean wait = false;;
	public boolean getWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		
		this.wait = wait;
		initAfterGameLoad();
	}

	public void initAfterGameLoad() {
		if(wait == false){
			//do stuff  this will be for all the really obscure bugs that I don't care about right now.
			for(IExecute ex: executeList){
				ex.execute();
			}
			executeList.clear();
			
		}
	}

	
	
	public com.google.gwt.user.client.Timer timer ;
	@Override
	public boolean run() {
		
		
		return EntryPoint.game.run();
		
		
	}
	public void cancel() {
		if(timer != null){
			timer.cancel();
		}
		
	}
	public static boolean reinit = false;
	@Override
	public void scheduleRepeating(int i) {
		if(timer == null){
			timer =  new com.google.gwt.user.client.Timer() {
				
				@Override
				public void run() {
					if(wait){
						return;
					}
					if(reinit){
						reinit = false;
						
						initAfterGameLoad();
					}
					
					if(!GWTTimer.this.run()){
						
						this.cancel();
					}
					
					
				}
			};
		}
		
		timer.scheduleRepeating(i);
	}

}
