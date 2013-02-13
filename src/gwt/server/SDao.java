package gwt.server;

import com.google.appengine.api.files.AppEngineFile;

import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.server.datamodel.FileResource;
import gwt.server.datamodel.FResourceManager;
import gwt.server.datamodel.GUser;
import gwt.server.datamodel.SaveGame;
import gwt.server.datamodel.ServerBag;
import gwt.server.datamodel.ServerCreation;
import gwt.server.datamodel.ServerGame;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.ServerTree;

public class SDao {
	public static Dao<ServerGame> getServerGameDao(){
		return new Dao<ServerGame>(ServerGame.class);
	}
	
	public static Dao<SaveGame> getSaveGameDao(){
		return new Dao<SaveGame>(SaveGame.class);
	}
	public static Dao<ServerCreation> getCreationDao(){
		return new Dao<ServerCreation>(ServerCreation.class);
	}
	
	public static Dao<ServerTree> getTreeDao(){
		return new Dao<ServerTree>(ServerTree.class);
	}
	
	public static Dao<AppEngineFile> getAppEngineFileDao(){
		return new Dao<AppEngineFile>(AppEngineFile.class);
	}
	
	public static Dao<GUser> getGUserDao(){
		return new Dao<GUser>(GUser.class);
	}
	
	public static Dao<ServerBag> getServerBagDao(){
		return new Dao<ServerBag>(ServerBag.class);
	}
	
	public static Dao<JsonData> getJsonDataDao(){
		return new Dao<JsonData>(JsonData.class);
	}
	
	public static Dao<FileResource> getFResourceDao(){
		return new Dao<FileResource>(FileResource.class);
	}
	
	public static Dao<FResourceManager> getFResourceManagerDao(){
		return new Dao<FResourceManager>(FResourceManager.class);
	}
	
}
