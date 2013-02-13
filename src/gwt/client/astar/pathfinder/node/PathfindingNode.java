package gwt.client.astar.pathfinder.node;

/**
 * Describes part of the map for a pathfinding algorithm
 * 
 * @author Mindgamer
 */
public interface PathfindingNode extends Comparable {
	
	/** Identifies the nodeList as unasigned */
	public final int NODE_LIST_UNASSIGNED = 0;
	/** Identifies the nodeList as on Open List */
	public final int NODE_ON_OPEN_LIST = 1;
	/** Identifies the nodeList as on Closed List */
	public final int NODE_ON_CLOSED_LIST = 2;
	
	/**
	 * Get the parent node of this node. Used for tracing path.
	 * 
	 * @return the parent
	 */
	public PathfindingNode getParent();

	/**
	 * @param parent the parent to set
	 */
	public void setParent(PathfindingNode parent);

	/**
	 * @return the x
	 */
	public int getX();

	/**
	 * @return the y
	 */
	public int getY();
	
	/**
	 * Return the heuristic cost of this node.
	 * H in A* terms
	 * 
	 * @return the costHeuristic
	 */
	public int getCostHeuristic();

	/**
	 * Set the heuristic cost of this node.
	 * H in A* terms
	 * 
	 * @param costHeuristic the costHeuristic to set
	 */
	public void setCostHeuristic(int costHeuristic);

	/**
	 * Return the movement cost so far to this node.
	 * G in A* terms
	 * 
	 * @return the costSoFar
	 */
	public int getCostSoFar();

	/**
	 * Set the movement cost so far to this node.
	 * G in A* terms
	 * 
	 * @param costSoFar the costSoFar to set
	 */
	public void setCostSoFar(int costSoFar);

	/**
	 * Return the total path cost for this node. 
	 * F in A* terms
	 * 
	 * @return the costTotal
	 */
	public int getCostTotal();

	/**
	 * Set the total path cost for this node. 
	 * F in A* terms
	 * 
	 * @param costTotal the costTotal to set
	 */
	public void setCostTotal(int costTotal);
	
	/**
	 * Returns which list the node has been assigned to by the path-finder. This value 
	 * can be incorrect if it has not been set in the OpenList.add(), OpenList.remove() 
	 * etc. method explicitly.<br>
	 * <br>
	 * Accepted values are:<br>
	 * PathfindingNode.NODE_LIST_UNASSIGNED<br>
	 * PathfindingNode.NODE_ON_OPEN_LIST<br>
	 * PathfindingNode.NODE_ON_CLOSED_LIST<br>
	 * 
	 * @return the nodeList
	 */
	public int getActiveList();

	/**
	 * Sets which list the node has been assigned to by the path-finder. This value 
	 * must be explicitly set in the OpenList.add(), OpenList.remove() etc. methods.<br>
	 * <br>
	 * Accepted values are:<br>
	 * PathfindingNode.NODE_LIST_UNASSIGNED<br>
	 * PathfindingNode.NODE_ON_OPEN_LIST<br>
	 * PathfindingNode.NODE_ON_CLOSED_LIST<br>
	 * 
	 * @param nodeList the nodeList to set
	 */
	public void setActiveList(int nodeList);
	
}
