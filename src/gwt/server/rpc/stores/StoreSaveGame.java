package gwt.server.rpc.stores;


import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

import gwt.client.main.Game;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.SaveGame;
import gwt.server.datamodel.ServerGame;
import gwt.server.rpc.gets.GetSaveGame;
import gwt.shared.datamodel.ClientSaveGame;
import gwt.shared.datamodel.IClientObject;




public class StoreSaveGame implements gwt.server.IStore {

	@Override
	public void store(String prevName, String parentName, IClientObject obj) {
		PersonLoginInfo info = LoginService.login(null,
				null);
		if (!info.loginInfo.isLoggedIn()) {			
			return;
		}
		
		ClientSaveGame csavegame = (ClientSaveGame) obj;
		long gamekey =Long.parseLong( parentName);
		
		SaveGame savegame=GetSaveGame.getSaveGame(prevName, info, gamekey);
		if(savegame == null){
			savegame = new SaveGame(gamekey,prevName);
		}
		
		
		savegame.addMap(csavegame.prevMapName,csavegame.prevMap);
		if(csavegame.currentJson != null){
			savegame.currentJson = csavegame.currentJson;
		}
		if(csavegame.data != null){
			savegame.setData(csavegame.data);	
		}
		
		savegame.ownerId = info.person.id;
		//SDao.getJsonDataDao().put(savegame.jsonData);
		
		Key<SaveGame> k=SDao.getSaveGameDao().put(savegame);
		
		
		//SDao.getGUserDao().put(info.person);
		
		
		
		
		
	}

}
