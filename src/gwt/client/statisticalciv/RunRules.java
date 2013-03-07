package gwt.client.statisticalciv;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class RunRules extends VParams {

	public static boolean pause;
	public RunRules(Object... vp) {
		super(vp);
	}

	public RunRules() {
		// TODO Auto-generated constructor stub
	}
	boolean setup = false;
	public void runSetup(){
		if(pause){
			return;
		}
		if(!setup){
			final PBase pb=new PBase("desert", 1);
			pb.put("rock", 2);
			pb.put("tree", 3);
			pb.put("default", 4);
			for (FullMapData fmd : EntryPoint.game.getMapArea().getMap()) {
				for (final HashMapData hmd : fmd) {
					int growth = pb.getInt("default"); // get
					MapData gate = hmd.getMapData(VConstants.gate);
					if(gate != null){
						growth =pb.getInt(gate.getValue());
					}
					MapData under = hmd.getMapData(VConstants.under);
					if(under != null){
						growth =pb.getInt(under.getValue());
					}
					hmd.put("growth",growth);
				
				}
			}

		}
	}
	@Override
	public void execute(final Map<String, Object> map) {
		runSetup();
		List<VParams> list = (List<VParams>) getList(VConstants.vparams);
		for (VParams v : list) {
			v.init();
		}
		for (FullMapData fmd : EntryPoint.game.getMapArea().getMap()) {
			for (final HashMapData hmd : fmd) {
				fmd.getNearby(hmd, new GetForNearby<HashMapData>(fmd) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						List<VParams> list = (List<VParams>) getList(VConstants.vparams);
						for (VParams v : list) {

							map.put(VConstants.main, hmd);
							map.put(AttachUtil.OBJECT, hashmapdata);
							v.execute(map);
						}
						return super.get(hashmapdata);
					}
				}, 1);
				// HashMapData north = fmd.getData(hmd.getX(), hmd.getY()-1);
				// HashMapData south = fmd.getData(hmd.getX(), hmd.getY()+1);
				// HashMapData east = fmd.getData(hmd.getX()+1, hmd.getY());
				// HashMapData west = fmd.getData(hmd.getX()-1, hmd.getY());
				// for(HashMapData h :new HashMapData[]{north,south,east,west}){
				// if(h == null){
				// continue;
				// }
				// List<VParams> list =
				// (List<VParams>)getList(VConstants.vparams);
				// for(VParams v : list){
				//
				// map.put(VConstants.main, hmd);
				// map.put(AttachUtil.OBJECT, h);
				// v.execute(map);
				// }
				// }
			}
		}
		super.execute(map);
		EntryPoint.game.getHtmlOut().displayAreaMap(EntryPoint.game.getHtmlOut().currentFMD);
		EntryPoint.game.getHtmlOut().displaySymbolicMap(EntryPoint.game.getMapArea().getMap());

	}

	@Override
	public PBase clone() {
		// TODO Auto-generated method stub
		return new RunRules().copyProperties(this);
	}
}
