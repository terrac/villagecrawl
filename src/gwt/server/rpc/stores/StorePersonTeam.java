package gwt.server.rpc.stores;

import gwt.server.IStore;
import gwt.server.SDao;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.IClientObject;

public class StorePersonTeam implements IStore {

	@Override
	public void store(String prevName, String parentName, IClientObject obj) {
		Long gkey=Long.parseLong(parentName);
		
		ServerGame sg=SDao.getServerGameDao().getRN(gkey);
		
		//save to an object that stores the user id, the game id, and the name
		
		//on loading a url we check for such objects
	}

}
