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
import gwt.client.map.MapData;
import gwt.client.output.OutputDirector;

import java.util.List;




public class WaitMove extends OObjectB{

	public WaitMove() {
		// TODO Auto-generated constructor stub
	}
	
	public WaitMove(int time) {
		super();
		put(VConstants.time,time);
	}
	public WaitMove(String overlay,int time) {
		this(time);
		put(VConstants.overlay,overlay);
		
	}
	HashMapData hmd;
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		if(hmd == null){
			hmd = person.getParent();
		}
//		if(hmd == person.getParent()){
//			addToList(person, new MoveRandomHashMapData(1, "waitmove"));
//		} else {
			addToList(person, new Move(hmd, "waitmove"));
//		}
		int time = getInt(VConstants.time);
		time--;
		put(VConstants.time,time);
		if(time < 0){
			return new Returnable();
		}
		return new Returnable(true,1);
	}

	
	@Override
	public OObject clone() {
		
		return new WaitMove().copyProperties(this);
	}
	
	
}
