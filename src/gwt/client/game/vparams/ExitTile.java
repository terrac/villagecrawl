package gwt.client.game.vparams;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Blob;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.buildgame;
import gwt.client.game.util.FlowControlException;
import gwt.client.item.SimpleMD;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.client.output.html.GCanvas;
import gwt.client.rpc.GWTTimer;
import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.IExecute;
import gwt.client.rpc.SendObject;
import gwt.client.rpc.SendObjectAsync;
import gwt.server.datamodel.SaveGame;
import gwt.shared.datamodel.ClientSaveGame;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.JsonDataList;
import gwt.shared.datamodel.VParams;

public class ExitTile extends GoTo {

	public ExitTile() {

	}

	public ExitTile(String jsonname) {
		put(VConstants.name, jsonname);
	}
	
	public ExitTile(String jsonname,boolean remove) {
		put(VConstants.name, jsonname);
		put(VConstants.remove,remove);
	}

	public ExitTile(String jsonname, String entrance) {
		this(jsonname);
		put(VConstants.entrance, entrance);
	}

	public ExitTile(String jsonname, String entrance, VParams runOnStart) {
		this(jsonname, entrance);

		put(AttachUtil.mapstart, runOnStart);
	}

	@Override
	public PBase clone() {
		return new ExitTile().copyProperties(this);
	}

	@Override
	public boolean saveAndReload(FullMapData hmd) {
		return saveAndReload(this, hmd);
	}

	/**
	 * Sets the current json and human and playerlist and then reloads the
	 * current map
	 * 
	 * @param hmd
	 */
	public static boolean saveAndReload(PBase current, FullMapData fmd) {
		List<LivingBeing> savelist = GameUtil.getPlayerTeam(fmd.people);
		List<LivingBeing> omaprefuse = (List<LivingBeing>) EntryPoint.game
				.getTemporary().get(VConstants.savedpeople);
		if (omaprefuse != null) {
			savelist.addAll(omaprefuse);
		}
		String gamekey = Window.Location.getParameter("gamekey");
		SendObjectAsync soa = GWT.create(SendObject.class);
		// top level object + save
		PBase pb = new PBase();
		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.savedpeople, savelist);
		PBase human = EntryPoint.game.getPBase(VConstants.human);
		human.put(VConstants.previous, EntryPoint.game.getName());
		pb.put(VConstants.human, human);
		human.put(VConstants.entrance, current.get(VConstants.entrance));
		if (current.containsKey(AttachUtil.mapstart)) {
			AttachUtil.attach(AttachUtil.mapstart, current
					.get(AttachUtil.mapstart), human);
		}

		for (LivingBeing lb : savelist) {
			if (lb.getParent() != null) {
				lb.getParent().remove(lb);
			}

		}

		PBase mpb = new PBase();
		mpb.put(VConstants.classname, Game.class.getName());
		mpb.put(VConstants.savedmap, EntryPoint.game.getMapArea().getMap());
		
		String cached = EntryPoint.game.jsonCache.get(
				current.getS(VConstants.name));
		if(cached == null){
			cached = EntryPoint.game.jsonCache.get(current.getS(VConstants.jsoncache));
		}
		if (cached != null) {
			// put savedmap in list of savedmaps and
			// have the save side save either one or multiple if there is a list
			// of savedmaps

			// call game.execute

			Game.csg.put(EntryPoint.game.getName(), new ClientSaveGame(
					EntryPoint.game.getName(), mpb.export(), null, null));
			//EntryPoint.game.getType(VConstants.jsoncache).put(EntryPoint.game.getName(), EntryPoint.game);
			EntryPoint.game.getHtmlOut().remove(EntryPoint.game.getHtmlOut().symbolicShell);
			EntryPoint.game.getHtmlOut().symbolicShell = null;
			for(GCanvas gc :EntryPoint.game.getHtmlOut().fmdMap.values()){
				EntryPoint.game.getHtmlOut().remove(gc);
				//gc.getContext2d().clearRect(0, 0, gc.getCoordinateSpaceWidth(), gc.getCoordinateSpaceHeight());
			}
//			Iterator<Entry<String,Object>> it=EntryPoint.game.getObjMap().entrySet().iterator();
//			while(it.hasNext()){
//				Entry<String,Object> eso=it.next();
//				if(!Arrays.asList(protKeys).contains(eso.getKey())){					
//					it.remove();
//				}
//			}
			
			
			//EntryPoint.game.getObjMap().putAll(cached.getObjMap());
			
			
			EntryPoint.game.setMapArea(null);
			EntryPoint.game.put(VConstants.savedmap, null);
			EntryPoint.game.getHtmlOut().currentFMD = null;
			buildgame.parseFile(EntryPoint.game, cached);
			EntryPoint.game.put(VConstants.name, current.get(VConstants.name));
			
			EntryPoint.game.put(VConstants.savedpeople, savelist);
			//EntryPoint.game.put(VConstants.human, pb);
			EntryPoint.game.getHtmlOut().fmdMap.clear();
			//buildgame.afterParse(EntryPoint.game);
			
			
			String nextmap = current.getS(VConstants.name);
			ClientSaveGame csg=Game.csg.get(nextmap);
			
			if(csg != null){
				buildgame.parseFile(EntryPoint.game, csg.prevMap);
			}
			
			
			
			OutputDirector.timer.executeList.add(new IExecute() {

				@Override
				public void execute() {
					EntryPoint.game.execute();
				}
			});
			
			throw new FlowControlException();
		}
		Game.csg.put(EntryPoint.game.getName(), new ClientSaveGame(EntryPoint.game.getName(), mpb
				.export(), current.getS(VConstants.name), pb.export()));

		soa.sendObjectDefault(Game.csg.values().toArray(new ClientSaveGame[0]),
				gamekey, VConstants.temporary, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window
								.alert("loading the next map resulted in failure of saving your next position to the server");
					}

					@Override
					public void onSuccess(Void result) {
						Window.Location.reload();
					}

				});

		return true;

	}
	
	static String[] protKeys = new String[]{VConstants.jsoncache};
}
