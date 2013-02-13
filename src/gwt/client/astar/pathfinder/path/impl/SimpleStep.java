/**
 * Rise to the Stars
 */
package gwt.client.astar.pathfinder.path.impl;

import gwt.client.astar.pathfinder.path.Step;

/**
 * A single step within the path
 * 
 * @author Mindgamer
 */
public class SimpleStep implements Step {
	
	/** The x coordinate at the given step */
	private int x;
	/** The y coordinate at the given step */
	private int y;
	
	/**
	 * Create a new SimpleStep
	 * 
	 * @param x The x coordinate of the new step
	 * @param y The y coordinate of the new step
	 */
	public SimpleStep(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int hashCode() {
		return x*y;
	}

	public boolean equals(Object other) {
		if (other instanceof Step) {
			Step o = (Step) other;
			
			return (o.getX() == x) && (o.getY() == y);
		}
		
		return false;
	}
}
