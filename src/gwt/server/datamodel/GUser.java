package gwt.server.datamodel;


import java.util.ArrayList;
import java.util.List;

import gwt.server.SDao;
import gwt.server.build.BuildFeatures;
import gwt.shared.ClientBuild;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;


public class GUser {
	public GUser() {
	
	}
	public GUser(String userId, String nickname) {
		id = userId;
		displayName = nickname;
		resourceManager=SDao.getFResourceManagerDao().put( new FResourceManager());
	}
	@Id
	public String id;
	public String displayName;
	
	public List<Key<ServerGame>> sglist;
	
	public String getDisplayName() {
		return displayName;
	}
	public Key<GUser> getKey(){
		return new Key(GUser.class,id);
	}
	public void add(Key<ServerGame> sgk){
		if(sglist == null){
			sglist = new ArrayList();
		}
		sglist.add(sgk);
	}
	public List<ServerGame> getGamesByCreator() {
		if(sglist == null){
//			sglist = new ArrayList<Key<ServerGame>>();
//			ServerGame sg = new ServerGame("new game");
//			
//			JsonData jd = new JsonData("new json",this);
//			jd.setData(new Blob(ClientBuild.doDC1().export().getBytes()));
//			Key k=SDao.getJsonDataDao().put(jd);
//			sg.jsonData = new ArrayList<Key<JsonData>>();
//			sg.jsonData.add(k);
//			sglist.add(SDao.getServerGameDao().put(sg));
			BuildFeatures.build(this);
		}
		
		List<ServerGame> sgl = new ArrayList<ServerGame>();
		for(Key<ServerGame> k : sglist){
			ServerGame sg=SDao.getServerGameDao().getRN(k);
			if(sg == null){
				continue;
			}
			sgl.add(sg);
		}
		
		return sgl;
	}
	
	public List<String> titles = new ArrayList<String>();
	public boolean hasTitle(String string) {

		return titles.contains(string);
	}
	
	public Key<FResourceManager> resourceManager ;

	public FResourceManager getResourceManager() {

		return SDao.getFResourceManagerDao().get(resourceManager);
	}
}
