package gwt.client.main;

import java.io.Serializable;

public class Returnable implements Serializable{
	boolean shouldcontinue = false;
	int timesegment = 1;
	boolean breakchain = false;
	public Returnable(){
			
		}
	
	public Returnable(int timesegment){
		this.timesegment = timesegment;
	}
	
	public Returnable(boolean shouldcontinue) {
		super();
		this.shouldcontinue = shouldcontinue;
	}

	public boolean isShouldcontinue() {
		return shouldcontinue;
	}
	@Override
	public String toString() {
		return "Returnable [breakchain=" + breakchain + ", shouldcontinue="
				+ shouldcontinue + ", timesegment=" + timesegment + "]";
	}
	public void setShouldcontinue(boolean shouldcontinue) {
		this.shouldcontinue = shouldcontinue;
	}
	public int getTimesegment() {
		return timesegment;
	}
	public void setTimesegment(int timesegment) {
		this.timesegment = timesegment;
	}
	public Returnable(boolean shouldcontinue, int timesegment) {
		super();
		this.shouldcontinue = shouldcontinue;
		this.timesegment = timesegment;
	}
	public Returnable(boolean shouldcontinue, int timesegment,
			boolean breakchain) {
		this(shouldcontinue,timesegment);
		this.breakchain = breakchain;
	}
	public boolean isBreakchain() {
		return breakchain;
	}
	public void setBreakchain(boolean breakchain) {
		this.breakchain = breakchain;
	}
	
	
}
