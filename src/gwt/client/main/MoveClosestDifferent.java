 package gwt.client.main;

import gwt.client.astar.pathfinder.algorithm.PathFinder;
import gwt.client.astar.pathfinder.algorithm.impl.AStarPathFinder;
import gwt.client.astar.pathfinder.heuristic.impl.AStarHeuristic;
import gwt.client.astar.pathfinder.path.Path;
import gwt.client.astar.pathfinder.path.Step;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

import java.util.List;




public class MoveClosestDifferent extends OObjectB{

	public MoveClosestDifferent() {
	}
	

	public MoveClosestDifferent(String key, String value,boolean not, int radius) {
		super();
		put(VConstants.key, key);
		put(VConstants.value,value);
		put(VConstants.not,not);
		put(VConstants.radius, radius);
	}
	

	public Returnable execute(FullMapData map, LivingBeing person) {
		
		MapData md;
		
		md=map.getNearKeyValueNot(getS(VConstants.key),person.getS(getS(VConstants.value)), person.getParent(), getInt(VConstants.radius));
		
			
		if(md == null){
			//throw new IllegalArgumentException(key+" "+value);
			return null;
		}
		person.getTemplate().pending.add(new Move((HashMapData) md,"movenearest"+getS(VConstants.key)));
		
		
		return new Returnable(false,0);
	}

	@Override
	public OObject clone() {
		
		return (OObject) new MoveClosestDifferent().copyProperties(this);
	}
}
