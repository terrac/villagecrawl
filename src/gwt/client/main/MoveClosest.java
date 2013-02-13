 package gwt.client.main;

import gwt.client.EntryPoint;
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




public class MoveClosest extends OObjectB{

	public MoveClosest() {
	}
	

	public MoveClosest(String key, String value) {
		super();
		put(VConstants.key, key);
		put(VConstants.value,value);
		put(VConstants.radius, 20);
	}
	public MoveClosest(String key, String value,int radius) {
		this(key,value);
		put(VConstants.radius,radius);
	}
	
	public MoveClosest(String key, String value,String type) {
		this(key,value);
		put(VConstants.type,type);
	}

	

	public Returnable execute(FullMapData map, LivingBeing person) {
		
		MapData md = null;
		if(!containsKey(VConstants.type)||map.getListCreate(VConstants.type).contains(getS(VConstants.type))){
			md=map.getNearKeyValue(getS(VConstants.key),getS(VConstants.value), person.getParent(), getInt(VConstants.radius));
			
		}
	
				
		if(md == null&&containsKey(VConstants.type)){
			for(FullMapData fmd : EntryPoint.game.getMapArea().getMap()){
				List list = fmd.getList(VConstants.type);
				if(list == null||!list.contains(getS(VConstants.type))){
					continue;
				}
				if(md != null){
					break;
				}
				md=fmd.getAllKeyValue(getS(VConstants.key),getS(VConstants.value));
					
			}
		} 
		
			
		if(md == null){
			//throw new IllegalArgumentException(key+" "+value);
			return new Returnable(false, 1, true);
		}
//		if(Point.distance(person.getX(),person.getY(), ((IPhysical)md).getX(), ((IPhysical)md).getY()) < 3){
//			return null;
//		}
		person.getTemplate().pending.add(new Move((HashMapData) md,"movenearest"+getS(VConstants.key)));
		
		
		return new Returnable(false,0);
	}

	@Override
	public OObject clone() {
		
		return (OObject) new MoveClosest().copyProperties(this);
	}
}
