package gwt.server.rpc.gets;

import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.client.personality.Stats;
import gwt.client.rpc.LoginInfo;
import gwt.server.IGet;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.ServerGame;
import gwt.shared.buildjson;
import gwt.shared.datamodel.CString;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.JsonDataList;
import gwt.shared.datamodel.ServerTree;

public class GetGame implements IGet {

	@Override
	public IClientObject get(String parentName,
			Map<String, IClientObject> prevParams) {
		if (parentName == null) {
			return null;
		}
		ServerGame sg = SDao.getServerGameDao().getRN(
				Long.parseLong(parentName));
		if (sg == null) {
			return null;
		}
		prevParams.put("gamekey", new CString(parentName));
		List<String> jdl = new ArrayList();
		{
			
			for (JsonData j : sg.getMainJsonDatasByGame()) {
				jdl.add(new String(j.getData().getBytes()));
			}
		}
		
		
		return new JsonDataList(jdl);

	}
}
