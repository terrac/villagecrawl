package gwt.client.game.vparams;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Blob;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.SendObject;
import gwt.client.rpc.SendObjectAsync;
import gwt.server.datamodel.SaveGame;
import gwt.shared.datamodel.ClientSaveGame;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.JsonDataList;
import gwt.shared.datamodel.VParams;

public class Exit extends VParams {

	public Exit() {

	}

	public Exit(String jsonname) {
		put(VConstants.name, jsonname);
	}
	public Exit(String jsonname,String entrance) {
		this(jsonname);
		put(VConstants.entrance,entrance);
	}
	public Exit(String jsonname,String entrance, VParams runOnStart) {
		this(jsonname, entrance);
		
		put(AttachUtil.mapstart,runOnStart);
	}
	@Override
	public PBase clone() {
		return new Exit().copyProperties(this);
	}

	@Override
	public void execute(Map<String, Object> map) {
		ExitTile.saveAndReload(this, EntryPoint.game.getHtmlOut().currentFMD);
	}
}
