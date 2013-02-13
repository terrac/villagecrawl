package gwt.client.statisticalciv.generator.nomadic;

import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.shared.datamodel.VParams;

public class FishingGenerator extends VParams {

	public FishingGenerator() {
	}

	/**
	 * 
	 

    i fishing on the coast
      1 make some ocean tiles in the appropriate direction
      2 have the population be right near there
      3 future movement is goverend by closeness to coast*/
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		FullMapData fmd = (FullMapData) map.get(VConstants.fullmapdata);
		
		HashMapData water=hmdMain.getParent().getNearKeyValue(VConstants.obstacle, VConstants.water, hmdMain, 2);
		Direction d=Direction.getDirection(water, hmdMain);
		//probably should do this for each water tile
		
		//get the middle point
		//have direction color everything beyond that middle point water
		Point middle = Point.middle(fmd.getXsize(), fmd.getYsize());
		int length = Point.distance(middle, new Point(0,0))/2;
		Point newMiddle = new Point(d.getX() * fmd.getXsize(),d.getY() * fmd.getYsize());
		fmd.getParent().getNearby(newMiddle, new GetForNearby<FullMapData>(fmd) {
			@Override
			public FullMapData get(FullMapData hashmapdata) {
				hashmapdata.put(new SimpleMD(VConstants.gate, VConstants.water));
				return super.get(hashmapdata);
			}
		}, length);
		
		//create a second afterexecute that 
		//takes some of the people created for food and gives them the fishing template
		//it also puts spears in their hands
	}

	@Override
	public PBase clone() {
		return new FishingGenerator().copyProperties(this);
	}
}
