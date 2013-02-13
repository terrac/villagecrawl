package gwt.client.astar.pathfinder.openlist;



import gwt.client.astar.pathfinder.node.PathfindingNode;


/**
 * An interface describing the list used for an open list in an A* search algorithm.
 * 
 * @author Mindgamer
 */
public interface OpenList {

	/**
	 * Empty the list
	 */
	public void clear();
	
	/**
	 * Return the first element and remove it then from the list
	 *  
	 * @return The first element from the list
	 */
	public PathfindingNode getFirstAndRemove();
	
	/**
	 * Add an element to the list.
	 * 
	 * @param node The element to add
	 */
	public void add(PathfindingNode node);
	
	/**
	 * Resort the specified Node into the correct position
	 * 
	 * @param node The element which has changed
	 */
	public void resort(PathfindingNode node);
	
	/**
	 * Returns an iterator over the entire Open list.<br>
	 * <b>TODO:</b> Modify all implementations so that the Iterator always 
	 * returns PathfindingNode-s, not generic Objects
	 * 
	 * @return Iterator
	 */
	public PathfindingNode[] getOpenList();
	
	/**
	 * Get the number of elements in the list
	 * 
	 * @return The number of element in the list
	 */
	public int size();
	
}
