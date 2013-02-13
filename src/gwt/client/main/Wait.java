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




public class Wait extends OObjectB{

	public Wait() {
		// TODO Auto-generated constructor stub
	}
	
	public Wait(int time) {
		super();
		put(VConstants.time,time);
	}
	public Wait(String overlay,int time) {
		this(time);
		put(VConstants.overlay,overlay);
		
	}
	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
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
		
		return new Wait().copyProperties(this);
	}
	
	
}
