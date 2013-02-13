package gwt.client.astar.pathfinder.heuristic.impl;

import gwt.client.astar.pathfinder.heuristic.Heuristic;
import gwt.client.astar.world.Mover;
import gwt.client.astar.world.WorldMap;


/**
 * A class providing a cost for a given tile based
 * on a target location and entity being moved. This heuristic controls
 * what priority is placed on different tiles during the search for a path
 * 
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile. Distance squared is used.
 */
public class AStarHeuristic implements Heuristic {

	/**
	 * Get the additional heuristic cost of the given tile. This controls the
	 * order in which tiles are searched while attempting to find a path to the 
	 * target location. The lower the cost the more likely the tile will
	 * be searched.
	 * 
	 * @param map The map on which the path is being found
	 * @param unit The entity that is moving along the path
	 * @param x The x coordinate of the tile being evaluated
	 * @param y The y coordinate of the tile being evaluated
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @return The cost associated with the given tile
	 */
	public int getCost(WorldMap map, Mover unit, int x, int y, int tx, int ty) {		
		int dx = Math.abs(x - tx);
		int dy = Math.abs(y - ty);
		
		if (dx > dy){
			//System.out.println("dx"+dx+" > dy"+dy+" = H" + ((WorldMap.DIAGONAL_MOVEMENT_COST * dy) + (WorldMap.HORIZONTAL_MOVEMENT_COST * (dx-dy))));
			return (WorldMap.DIAGONAL_MOVEMENT_COST * dy) + (WorldMap.HORIZONTAL_MOVEMENT_COST * (dx-dy));
		}else{
			//System.out.println("dy"+dy+" >= dx"+dx+" = H" + ((WorldMap.DIAGONAL_MOVEMENT_COST * dx) + (WorldMap.HORIZONTAL_MOVEMENT_COST * (dy-dx))));
			return (WorldMap.DIAGONAL_MOVEMENT_COST * dx) + (WorldMap.HORIZONTAL_MOVEMENT_COST * (dy-dx));
		}
	}

}
