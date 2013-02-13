package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.PhysicalMapData;

public class RunAway extends OObject {
	public RunAway() {
		// TODO Auto-generated constructor stub
	}
	IPhysical md;
	public RunAway(IPhysical md) {
		super();
		this.md = md;
	}
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		//find position of md,
		
		
		
		
		Direction dir=Direction.getDirection(md,person);
		
		
		if(fullMapData.getParent().getXsize() == 1&&fullMapData.getParent().getYsize() == 1){
			Point p=fullMapData.getEdge(dir);
			addToList(person, new Move(fullMapData.getData( p),"runawaysamefmd"));
			return null;
		}

		person.setVariable(VConstants.runaway, md);
		
		FullMapData fmd=fullMapData.getParent().getData(fullMapData.getX() + dir.getX(), fullMapData.getY() + dir.getY());
		if(fmd == null){
			//if that direction is invalid panick and run away randomly
			addToList(person, new MoveRandomFullMapData(1,"runaway"));
			return null;
		}
		
		//addToList(person, new Move(fmd.getData(dir.getX() * 10, dir.getY() * 10),"runaway10"));
		addToList(person, new Move(fmd.getData( 10, 10),"runaway10"));
		//get the opposite direction of run away
		
		// run away
		
		//if on a different map move an additional 10 steps and then stop
		
		return null;
	}

	@Override
	public OObject clone() {
		return new RunAway(md);
	}
}
