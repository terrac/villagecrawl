package gwt.client.map;

import java.io.Serializable;

public class AgeData implements Serializable{
	
	public int age = 300;
	
	public boolean age(){
		age--;
		if(age <=0){
			return true;
		}
		return false;
	}
}
