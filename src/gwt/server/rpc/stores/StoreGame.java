package gwt.server.rpc.stores;

import gwt.client.main.Game;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.IClientObject;




public class StoreGame implements gwt.server.IStore {

	@Override
	public void store(String prevName, String parentName, IClientObject obj) {
		PersonLoginInfo info = LoginService.login(null,
				null);
		if (!info.loginInfo.isLoggedIn()) {			
			return;
		}
		Game game = (Game) obj;
		ServerGame sg = SDao.getServerGameDao().getChild(info.person.getKey(), ServerGame.class, "name", game.getName());
//		if(sg != null){
//			sg.setGame( game);
//		}else {
//			sg = new ServerGame(game);
//		}
		sg.user = info.person.getKey();
		SDao.getServerGameDao().put(sg);
		
		
		
		
		
	}

}
