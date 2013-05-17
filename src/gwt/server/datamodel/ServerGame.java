package gwt.server.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.server.SDao;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialized;

public class ServerGame {

	@Parent
	public Key<GUser> user;

	public ServerGame() {
	}

	public @Id
	Long id;
	public String name;

	public Key<ServerGame> getKey() {
		return new Key(ServerGame.class, id);
	}

	public String getName() {
		return name;
	}

	public List<Key<JsonData>> mainJsonData;
	public List<Key<JsonData>> otherJsonData;
	public Key<JsonData> startJson;
	public String prettyEdit;
	public long totalViews;

	public List<JsonData> getMainJsonDatasByGame() {
		if (mainJsonData == null) {
//			mainJsonData = new ArrayList<Key<JsonData>>();
//			mainJsonData.add(SDao.getJsonDataDao().put(
//					new JsonData("new", true, user.getName())));
		}
		List<JsonData> jdlist = getList(mainJsonData);
		return jdlist;
	}
	
	public List<JsonData> getOtherJsonDatasByGame() {		
		List<JsonData> jdlist = getList(otherJsonData);
		return jdlist;
	}

	private List<JsonData> getList(List<Key<JsonData>> jsonData) {
		List<JsonData> jdlist = new ArrayList<JsonData>();
		if(jsonData == null){
			return jdlist;
		}
		for (Key<JsonData> k : jsonData) {
			JsonData jd = SDao.getJsonDataDao().getRN(k);
			jdlist.add(jd);
		}
		return jdlist;
	}

	public ServerGame(String name) {
		super();
		this.name = name;
	}

	public void addMain(Key<JsonData> jd) {
		if (mainJsonData == null) {
			mainJsonData = new ArrayList<Key<JsonData>>();
		}
		mainJsonData.add(jd);

	}
	public void addOther(Key<JsonData> jd) {
		if (otherJsonData == null) {
			otherJsonData = new ArrayList<Key<JsonData>>();
		}
		otherJsonData.add(jd);

	}

	public JsonData getStartJson() {

		return SDao.getJsonDataDao().getRN(startJson);
	}

	

}
