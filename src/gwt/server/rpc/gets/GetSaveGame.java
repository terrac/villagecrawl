package gwt.server.rpc.gets;

import gwt.server.IGet;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.SaveGame;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.CString;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.JsonDataList;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.googlecode.objectify.Query;

public class GetSaveGame implements IGet {

	@Override
	public IClientObject get(String parentName,
			Map<String, IClientObject> prevParams) {

		PersonLoginInfo info = LoginService.login(null, null);
		
			
		

		CString cString = (CString) prevParams.get("gamekey");
		if (cString == null) {
			return null;
		}
		long gamekey = Long.parseLong(cString.value);
		SaveGame sg = null;
		if (info.loginInfo.isLoggedIn()) {
			sg= getSaveGame(parentName, info, gamekey);	
		}
		
		if (sg == null) {
			sg = new SaveGame(gamekey, parentName);
			ServerGame serGame = SDao.getServerGameDao().getRN(gamekey);
			sg.currentJson = serGame.getStartJson().getName();

		}
		JsonDataList jdl = new JsonDataList();
		if (sg.jsonData != null) {
			jdl.jsonData.add(new String(sg.jsonData.getData().getBytes()));
		}

		String jname = sg.currentJson;
		{
			JsonData cur = getJD(gamekey, jname);
			if (cur != null) {
				jdl.currentJson=new String(cur.getData().getBytes());

				if(cur.jsonCache != null){
					for (String jcn : cur.jsonCache) {
						JsonData cache = getJD(gamekey, jcn);
						if (cache != null) {
							jdl.jsonCache
									.put(jcn,new String(cache.getData().getBytes()));
						} else {
							throw new RuntimeException("did you forget a specific json element for this game?  Like "+jcn);
						}

					}
				}
			

			}
		}

		Query<JsonData> jd = SDao.getJsonDataDao().getQuery();
		jd.filter("gamekey", gamekey);
		jd.filter("name", sg.name + "/" + jname);
		JsonData map = jd.get();
		if (map != null) {

			jdl.jsonData.add(new String(map.getData().getBytes()));

		}
		return jdl;

	}

	private JsonData getJD(long gamekey, String jname) {
		Query<JsonData> jd = SDao.getJsonDataDao().getQuery();
		jd.filter("gamekey", gamekey);
		jd.filter("name", jname);

		JsonData cur = jd.get();
		return cur;
	}

	public static SaveGame getSaveGame(String parentName, PersonLoginInfo info,
			long gamekey) {
		Query<SaveGame> q = SDao.getSaveGameDao().getQuery();
		q.filter("gamekey", gamekey);
		q.filter("ownerId", info.person.id);
		q.filter("name", parentName);
		SaveGame sg = q.get();
		return sg;
	}

}
