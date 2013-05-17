package gwt.server.rpc.stores;

import gwt.server.IStore;
import gwt.server.SDao;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;

public class SaveJsonData implements IStore {

	@Override
	public void store(String prevName, String parentName, IClientObject obj) {
		
		Long jsonkey=Long.parseLong(parentName);
		JsonData toSave = (JsonData) obj;
		JsonData jd = SDao.getJsonDataDao().get(jsonkey);
		jd.setData(toSave.getData());
		SDao.getJsonDataDao().put(jd);
		
//		ServerGame sg=SDao.getServerGameDao().getRN(gkey);
//		for(JsonData jd :sg.getOtherJsonDatasByGame()){
//			if(jd.getName().equals(toSave.getName())){
//				jd.setData(toSave.getData());
//				SDao.getJsonDataDao().put(jd);
//			}
//		}
//		
//		SDao.getServerGameDao().put(sg);
		//save to an object that stores the user id, the game id, and the name
		
		//on loading a url we check for such objects
	}

}
