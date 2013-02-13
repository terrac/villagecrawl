package gwt.client.astar.pathfinder.algorithm.impl;

import java.util.ArrayList;
import java.util.Iterator;

import gwt.client.astar.pathfinder.algorithm.PathFinder;
import gwt.client.astar.pathfinder.heuristic.Heuristic;
import gwt.client.astar.pathfinder.node.PathfindingNode;
import gwt.client.astar.pathfinder.node.impl.SimpleNode;
import gwt.client.astar.pathfinder.openlist.OpenList;
import gwt.client.astar.pathfinder.openlist.impl.BinaryHeap;
import gwt.client.astar.pathfinder.path.Path;
import gwt.client.astar.pathfinder.path.impl.SimplePath;
import gwt.client.astar.world.Mover;
import gwt.client.astar.world.WorldMap;


/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path. 
 */
public class AStarPathFinder implements PathFinder{
	
	/** The map being searched */
	private WorldMap map;
	
	/** The complete set of nodes across the map */
	private PathfindingNode[][] nodes;
	
	/** The set of nodes that we do not yet consider fully searched */
	private OpenList open = new BinaryHeap();

	/** The set of nodes that have been searched through */
	private ArrayList<PathfindingNode> closed = new ArrayList<PathfindingNode>();
	
	/** The heuristic we're applying to determine which nodes to search first */
	private Heuristic heuristic;
	
	/**
	 * Create a path finder
	 *
	 * @param map The map to be searched
	 */
	public AStarPathFinder(WorldMap map, Heuristic heuristic){
		/* 
		 * Put map data into the pathfinder's node mapping
		 * TODO: Implement use of multi-square tiles?
		 */
		nodes = new SimpleNode[map.getWidth()][map.getHeight()];
		for (int x=0;x<map.getWidth();x++) {
			for (int y=0;y<map.getHeight();y++) {
				nodes[x][y] = new SimpleNode(x,y);
			}
		}
		this.map = map;
		this.heuristic = heuristic;
		
	}
	
	
	/**
	 * @see PathFinder#findPath(Mover, int, int, int, int)
	 */
	public Path findPath(Mover mover, int xSta, int ySta, int xTar, int yTar){
//		System.out.println();
//		System.out.println("=============== NEW SEARCH ==================");
		/*
		 * Check if start and target coordinates are the same - if they are, return
		 * Check if target path is walkable. If it is not, return
		 */
		if (!isValidLocationDontCheckBlock(mover, xSta, ySta, xTar, yTar)){
			return null;
		}
		
		Path path = new SimplePath();
		
		// Clear which map tiles have been visited by the pathfinder
		map.clearVisited();
		
		/* 
		 * Initial state for A*. The closed group is empty. Only the starting
		 * tile is in the open list.
		 * TODO: If multi-square tiles are implemented, check if sx and sy fall into 
		 * 		 a multi-tile. If they do, split up that tile for accuracy.
		 */
		nodes[xSta][ySta].setCostHeuristic(heuristic.getCost(map, mover, xSta, ySta, xTar, yTar));
		nodes[xSta][ySta].setCostSoFar(0);
		nodes[xSta][ySta].setCostTotal(nodes[xSta][ySta].getCostHeuristic());
		addToOpenList(nodes[xSta][ySta]);
		
		// This overwrites any previous values and lets us identify if path has been found
		nodes[xTar][yTar].setParent(null);
		
		while (open.size() != 0) {
			PathfindingNode currentNode = getFirstAndRemove();		// Look for the lowest F cost square on the open list
			addToClosedList(currentNode);							// Add it to closed list
//			System.out.println();
//			System.out.println("Processing neighbours of tile " + currentNode.getX() + ":" + currentNode.getY());
			if (currentNode.getX() == xTar && currentNode.getY() == yTar){
				break;
			}
			int xPro;
			int yPro;
			int movementCost;
			
			for (int x=-1;x<2;x++) {									// Search through all the neighbors of the current 
				for (int y=-1;y<2;y++) {								//		node evaluating them as next steps
					xPro = x + currentNode.getX();
					yPro = y + currentNode.getY();

					if ((x != 0) && (y != 0)){
						movementCost = WorldMap.DIAGONAL_MOVEMENT_COST;
					}else{
						movementCost = WorldMap.HORIZONTAL_MOVEMENT_COST;
					}
					
					if (isValidLocation(mover, currentNode.getX(), currentNode.getY(), xPro, yPro) &&
							nodes[xPro][yPro].getActiveList() != PathfindingNode.NODE_ON_CLOSED_LIST ) {
//						System.out.println();
//						System.out.println("Current tile " + xPro + ":" + yPro);
						if (nodes[xPro][yPro].getActiveList() != PathfindingNode.NODE_ON_OPEN_LIST){
//							System.out.println("Not yet on open list");
							/* 
							 * If the given node is not on the Open list yet then make the current square 
							 * the parent of this square, record the F, G, and H costs of the square and
							 * add the node to the open list
							 */
							nodes[xPro][yPro].setParent(currentNode);		// Save currentNode as the parent of the given node
							nodes[xPro][yPro].setCostHeuristic(				// Set the heuristic cost for the given tile
									heuristic.getCost(map, mover, xPro, yPro, xTar, yTar));
							nodes[xPro][yPro].setCostSoFar(					// Set the cost of moving so far for the given tile
									currentNode.getCostSoFar() + 
									movementCost);
//							System.out.println("SoFar = current ("+currentNode.getCostSoFar()+") + movement ("+movementCost+") = " + nodes[xPro][yPro].getCostSoFar());
							nodes[xPro][yPro].setCostTotal(					// Set the total cost F for the given tile
									nodes[xPro][yPro].getCostHeuristic() + 
									nodes[xPro][yPro].getCostSoFar());
							addToOpenList(nodes[xPro][yPro]);				// Add the Node to the Open list
						}else{												// If the given node WAS already on the Open list
//							System.out.println("Already on open list");
							/*
							 * If it is on the open list already, check to see if this path to that square 
							 * is better, using G cost as the measure. A lower G cost means that this is a 
							 * better path. If so, change the parent of the square to the current square, 
							 * and recalculate the G and F scores of the square. If you are keeping your 
							 * open list sorted by F score, you may need to resort the list to account for 
							 * the change.
							 */
//							System.out.println("Current=G"+nodes[xPro][yPro].getCostSoFar()+"H"+nodes[xPro][yPro].getCostHeuristic()+"F"+nodes[xPro][yPro].getCostTotal());
							if (nodes[xPro][yPro].getCostSoFar() > currentNode.getCostSoFar() + movementCost){
								nodes[xPro][yPro].setParent(currentNode);	// Set new parent
								nodes[xPro][yPro].setCostSoFar(				// Set new path cost through current tile
										currentNode.getCostSoFar() + 
										movementCost);
								nodes[xPro][yPro].setCostTotal(				// Calculate new total cost
										nodes[xPro][yPro].getCostHeuristic() + 
										nodes[xPro][yPro].getCostSoFar());
//								System.out.println("Changed to=G"+nodes[xPro][yPro].getCostSoFar()+"H"+nodes[xPro][yPro].getCostHeuristic()+"F"+nodes[xPro][yPro].getCostTotal());
								open.resort(nodes[xPro][yPro]);				// Trigger reorder in the Open List
							}
						}
					}// End of valid isValidLocation()
				}// For 2
			}// For 1
		}// End of while loop

		/*
		 * Since we've got an empty open list or we've run out of search 
		 * there was no path. Just return null
		 */ 
		if (nodes[xTar][yTar].getParent() == null) {
			return null;
		}
		
		/*
		 * At this point we've definitely found a path so we can uses the parent
		 * references of the nodes to find out way from the target location back
		 * to the start recording the nodes on the way.
		 */
		PathfindingNode target = nodes[xTar][yTar];
		while (target != nodes[xSta][ySta]) {
			path.prependStep(target.getX(), target.getY());
			target = target.getParent();
		}
		path.prependStep(xSta, ySta);
		
		clearClosedList();
		clearOpenList();
		
		return path;
		
	}
	
	
	
	/**
	 * Check if a given location is valid for the supplied mover
	 * 
	 * @param mover The unit that would hold a given location
	 * @param sx The starting x coordinate
	 * @param sy The starting y coordinate
	 * @param x The x coordinate of the location to check
	 * @param y The y coordinate of the location to check
	 * @return True if the location is valid for the given unit
	 */
	private boolean isValidLocation(Mover mover, int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidth()) || (y >= map.getHeight());
		if ((!invalid) && ((sx != x) || (sy != y))) {
			invalid = map.isBlocked(mover,sx,sy, x, y);
		}
		return !invalid;
	}
	private boolean isValidLocationDontCheckBlock(Mover mover, int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidth()) || (y >= map.getHeight());
		
		return !invalid;
	}
	/**
	 * Adds the specified PathfindingNode to the Open list and inside the node
	 * itself calls setActiveList(PathfindingNode.NODE_ON_OPEN_LIST)
	 * 
	 * @param node
	 */
	private void addToOpenList(PathfindingNode node){
		open.add(node);
		node.setActiveList(PathfindingNode.NODE_ON_OPEN_LIST);
		map.setVisited(node.getX(), node.getY());	// Mark the tile on the map as 'visited'

//		System.out.println("Adding " + node.getX() + ":" + node.getY() + "=G"+node.getCostSoFar()+"H"+node.getCostHeuristic()+"F"+node.getCostTotal());
	}
	
	/**
	 * Adds the specified PathfindingNode to the Closed list and inside the node
	 * itself calls setActiveList(PathfindingNode.NODE_ON_CLOSED_LIST)
	 * 
	 * @param node
	 */
	private void addToClosedList(PathfindingNode node){
		closed.add(node);
		node.setActiveList(PathfindingNode.NODE_ON_CLOSED_LIST);
	}
	
	/**
	 * Resets the activeList value in all the nodes to NODE_LIST_UNASSIGNED and
	 * removes all nodes from the Open List
	 */
	private void clearOpenList(){
		PathfindingNode temp;
		int size = open.size() + 1;
		PathfindingNode[] list = open.getOpenList();
		for (int i = 1; i < size; i++){
			temp = list[i];
			temp.setActiveList(PathfindingNode.NODE_LIST_UNASSIGNED);
		}
		open.clear();
	}
	
	/**
	 * Removes all nodes from the Closed List
	 */
	private void clearClosedList(){
		PathfindingNode temp;
		for (int i = 0; i < closed.size(); i++){
			temp = (PathfindingNode) closed.get(i);
			temp.setActiveList(PathfindingNode.NODE_LIST_UNASSIGNED);
		}
		closed.clear();
	}
	
	/**
	 * Return the first element and remove it then from the list
	 * 
	 * @return
	 */
	private PathfindingNode getFirstAndRemove(){
		PathfindingNode temp = open.getFirstAndRemove();
		temp.setActiveList(PathfindingNode.NODE_ON_CLOSED_LIST);
		return temp;
	}
	

	
	
	
}
