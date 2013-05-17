package gwt.server.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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

public class GameList {


	public GameList() {
	}

	public @Id
	Long id;

	public Key<GameList> getKey() {
		return new Key(GameList.class, id);
	}

	@Serialized
	public List<Popular> mostPopular = new ArrayList<GameList.Popular>();
	@Serialized
	public List<Popular> sharedGames = new ArrayList<GameList.Popular>();

	@Serialized
	Map<Long,Long> popularityMap = new HashMap<Long, Long>();
	
	@Serialized
	Map<Long,Long> mostPopularityMap = new HashMap<Long, Long>();
	
	Long minimumPopularity = 0L;
	public void incrementPopularity(ServerGame sg){
		if(!sharedGames.contains(new Popular(sg.getKey().getId(), 0L))){
			return;
		}
		sg.totalViews++;
		SDao.getServerGameDao().put(sg);
		
		
		if(minimumPopularity == null||minimumPopularity <sg.totalViews){
			if(!mostPopular.contains(sg.getKey().getId())){
				mostPopular.add(new Popular(sg.getKey().getId(),0L));
			}
			Collections.sort(mostPopular);
			if(mostPopular.size() > 100){
				mostPopular.remove(99);
			}
			
		}
		
		SDao.getGameListDao().put(this);
	}
	
	public static class Popular implements Comparable<Popular>,Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4322998473214710584L;
		public Long key;
		public Long count;
		@Override
		public int compareTo(Popular o) {
			//technically could cause issues I suppose
			return (int) (count - o.count);
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Popular){
				return key.equals(((Popular) obj).key);
			}
			return super.equals(obj);
		}
		public Popular(Long key, Long count) {
			super();
			this.key = key;
			this.count = count;
		}
		public Popular() {
		}
		
	}

	public static GameList getGameList() {
		GameList gl=SDao.getGameListDao().getQuery().get();
		if(gl == null){
			gl = new GameList();
			SDao.getGameListDao().put(gl);
			
		}
		return gl;
	}

	public void addToShared(long key) {
		
		Popular popular = new Popular(key,  0L);
		if(sharedGames.contains(popular)){
			return;
		}
		sharedGames.add(popular);
		SDao.getGameListDao().put(this);
	}

	public List<Popular> getMostPopular() {
		mostPopular = new ArrayList<GameList.Popular>();
		SDao.getGameListDao().put(this);
		return mostPopular;
	}

	public static boolean containsShared(long id2) {
		return getGameList().sharedGames.contains(new Popular(id2, 0L));
	}

}
