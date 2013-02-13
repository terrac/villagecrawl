package gwt.shared.datamodel;

public class ClientSaveGame implements IClientObject{

public ClientSaveGame() {

}
	
	
	public ClientSaveGame(String prevMapName, String prevMap, String currentJson,
		String data) {
	super();
	this.prevMapName = prevMapName;
	this.prevMap = prevMap;
	this.currentJson = currentJson;
	this.data = data;
}


	public String prevMapName;
	public String prevMap;
	public String currentJson;
	public String data;
	
	






}
