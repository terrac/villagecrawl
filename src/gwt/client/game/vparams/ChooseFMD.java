package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.SimpleMD;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class ChooseFMD extends VParams {

	public ChooseFMD() {

	}


	public ChooseFMD(Object... vp) {
		super(vp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmd=(HashMapData) map.get(AttachUtil.OBJECT);
		//pick the fmd closest to the same direction as the hmd is in
		FullMapData fmd = hmd.getParent();
		int x=fmd.getXsize()/2;
		int y=fmd.getYsize()/2;
		
		Direction dir = Direction.getDirection(hmd, new Point(x,y));
		
		
		Point pos = new Point(fmd.getX()+dir.getX(), fmd.getY()+dir.getY());
		System.out.println(pos);
		FullMapData newfmd = fmd.getParent().getData(pos);
		map.put(AttachUtil.OBJECT, newfmd);
		hmd.put(VConstants.portal, Point.getPBase(pos.x, pos.y));
		AttachUtil.attach(AttachUtil.personadded, new MovePerson(), hmd);
		newfmd.put(VConstants.unbuilt,hmd);
		super.execute(map);
	}

	@Override
	public PBase clone() {
		return new ChooseFMD().copyDeepProperties(this);
	}

}
