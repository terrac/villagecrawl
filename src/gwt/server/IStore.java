package gwt.server;

import gwt.shared.datamodel.IClientObject;



public interface IStore {
	
	//Think of this method like you would setting a specific object on a list
	
	void store(String prevName, String parentName,
			IClientObject obj);

}
