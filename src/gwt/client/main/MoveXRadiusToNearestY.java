package gwt.client.main;

import java.util.List;

import gwt.client.main.base.BaseMoveMap;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.AreaMap;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;

public class MoveXRadiusToNearestY extends BaseMoveMap{

	GetForNearby gfn;
	
	public MoveXRadiusToNearestY() {
	
	}
	public MoveXRadiusToNearestY(int radius, String descr, GetForNearby gfn) {
		super(radius, descr);
		this.gfn = gfn;
	}





	protected FullMapData findNewMD(FullMapData fullMapData, LivingBeing person) {
		
		
		return fullMapData.getParent().getNearby(person, gfn, getRadius());
	}
	@Override
	public OObject clone() {
		
		return (OObject) new MoveXRadiusToNearestY().copyProperties(this);
	}
	






	
}
