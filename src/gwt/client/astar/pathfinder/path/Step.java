package gwt.client.astar.pathfinder.path;

public interface Step {
	
	/**
	 * Get the x coordinate of the new step
	 * 
	 * @return The x coodindate of the new step
	 */
	public int getX();
	
	/**
	 * Get the y coordinate of the new step
	 * 
	 * @return The y coodindate of the new step
	 */
	public int getY();
	
	/**
	 * @see Object#hashCode()
	 */
	public int hashCode();
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other);

}
