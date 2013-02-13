package gwt.client.main;

import gwt.client.rpc.IExecute;

import java.util.ArrayList;
import java.util.List;

public abstract class Timer{
	public List<IExecute> executeList = new ArrayList<IExecute>();
	public abstract void scheduleRepeating(int i);

	public abstract boolean run();
	
	public abstract void setWait(boolean wait);
	public abstract boolean getWait();

	public void cancel() {
		
	}

	public abstract void initAfterGameLoad() ;

	
}
