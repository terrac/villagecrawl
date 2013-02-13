 package gwt.client.game;

import gwt.client.EntryPoint;
import gwt.client.astar.pathfinder.algorithm.PathFinder;
import gwt.client.astar.pathfinder.algorithm.impl.AStarPathFinder;
import gwt.client.astar.pathfinder.heuristic.impl.AStarHeuristic;
import gwt.client.astar.pathfinder.path.Path;
import gwt.client.astar.pathfinder.path.Step;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.getters.KeyValue;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;

import java.util.List;




public class MoveClosestNot extends OObject{

	
	public MoveClosestNot() {
		// TODO Auto-generated constructor stub
	}

	
	
	public MoveClosestNot(String not, int radius) {
		super();
		put(VConstants.not,not);
		put(VConstants.radius,radius);
	}



	
	public Returnable execute(FullMapData map, LivingBeing person) {
		
		MapData md = map.getNearO(person, getInt(VConstants.radius),new KeyValue(VConstants.obstacle,true),new KeyValue(VConstants.livingbeing,true),new KeyValue(VConstants.gate,true));
				//map.getNearKeyValue(VConstants.gate, null, person, getInt(VConstants.radius));	
		
		System.out.println(md);	
		if(md == null){
			//throw new IllegalArgumentException(key+" "+value);
			return null;
		}
//		if(Point.distance(person.getX(),person.getY(), ((IPhysical)md).getX(), ((IPhysical)md).getY()) < 3){
//			return null;
//		}
		person.getTemplate().pending.add(new Move((HashMapData) md,"movenearestnot "+getS(VConstants.not)));
		
		
		return new Returnable(false,0);
	}

	@Override
	public OObject clone() {
		
		return new MoveClosestNot().copyProperties(this);
	}
}
