package gwt.client.main;

import java.io.Serializable;



public class OneStringChar implements Serializable{
	public OneStringChar() {
	
	}
	public OneStringChar(Character character, String string) {
		super();
		this.character = character;
		this.string = string;
	}
	public  Character character;
	public  String string;
}
