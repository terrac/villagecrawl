package gwt.server.rpc.stores;

import gwt.client.main.Game;
import gwt.client.map.FullMapData;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.ServerBag;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.IClientObject;




public class SaveBag implements gwt.server.IStore {

	@Override
	public void store(String prevName, String parentName, IClientObject obj) {
		PersonLoginInfo info = LoginService.login(null,
				null);
		if (!info.loginInfo.isLoggedIn()) {			
			return;
		}
		FullMapData fmd= (FullMapData) obj;
		ServerBag sb=SDao.getServerBagDao().getChild(info.person.getKey(), ServerBag.class, "name", parentName);
		
		if(sb != null){
			sb.editBag = fmd;
		}else {
			sb = new ServerBag(info.person.getKey(),fmd,parentName);
		}
		
		SDao.getServerBagDao().put(sb);
		
		
		
		
		
	}

}
