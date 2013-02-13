package gwt.server.datamodel;

import gwt.server.SDao;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialized;

public class SaveGame implements IClientObject{

	
	public String ownerId;

	public SaveGame() {
	}

	public @Id
	Long id;
	public Key<SaveGame> getKey(){
		return new Key(SaveGame.class,id);
	}

	
	public String currentJson;
	@Serialized
	public JsonData jsonData ;

	List<Key<JsonData>> mapList;

	public Long gamekey;
	public String name;
	public SaveGame(Long gamekey,String name) {
		this.gamekey = gamekey;
		this.name = name;
		
	}
	public void setData(String data) {
		jsonData = new JsonData();
		jsonData.setData(new Blob(data.getBytes()));
		
	}
	public void addMap(String prevMapName, String prevMap) {
		// TODO Auto-generated method stub
		JsonData entity = new JsonData(name+"/"+prevMapName,false,gamekey);
		entity.setData(prevMap);
		entity.ownerId =id;
		SDao.getJsonDataDao().put(entity);
		
	}





}
