package gwt.server.rpc;

import gwt.client.rpc.GetObject;
import gwt.server.IGet;
import gwt.server.rpc.gets.GetGame;
import gwt.server.rpc.gets.GetJsonData;
import gwt.server.rpc.gets.GetLogin;
import gwt.server.rpc.gets.GetSaveGame;
import gwt.shared.datamodel.IClientObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GetObjectImpl extends RemoteServiceServlet implements
		GetObject {

	public static Map<String,IGet> defaultMap = new HashMap<String, IGet>();
	static {
		//defaultMap.put("gametree",  new GetGameTree());
		defaultMap.put("game",  new GetGame());
		defaultMap.put("savegame",  new GetSaveGame());
		defaultMap.put("login",  new GetLogin());
		defaultMap.put("jsondata",  new GetJsonData());
		
	}
	@Override
	public Map<String,IClientObject> getObject( String[] types, String[] names) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		Map<String,IClientObject> map = new HashMap<String, IClientObject>();
		int count = 0;
		//map.put("site", new CString(site));
		
		for(String a : types){
			IGet iGet = defaultMap.get(a);
			if(iGet == null){
				throw new RuntimeException("Missing type:"+a+" in get");
			}
			map.put(a,  iGet.get(names[count],map));
			count++;
		}
//		for(String a :map.keySet()){
//			if(map.get(a) == null){
//				throw new RuntimeException(a+" Is null");
//			}
//		}
		
	    return  map;
	    
		
	}
	public Map<String,IClientObject> getObject(String type, String name) throws IllegalArgumentException {
		return getObject(new String[]{type},new String[]{name});
		
	    
		
	}

	
}
