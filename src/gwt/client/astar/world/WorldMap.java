package gwt.client.astar.world;


public interface WorldMap {
	
	/** The default map width in tiles */
	public static final int DEFAULT_WIDTH = 30;
	/** The default map height in tiles */
	public static final int DEFAULT_HEIGHT = 30;
	
	/** Indicate grass terrain at a given location */
	public static final int TERRAIN_GRASS = 0;
	/** Indicate water terrain at a given location */
	public static final int TERRAIN_WATER = 1;
	/** Indicate trees terrain at a given location */
	public static final int TERRAIN_TREES = 2;
	
	/** Indicate a plane is at a given location */
	public static final int PLANE = 3;
	/** Indicate a boat is at a given location */
	public static final int BOAT = 4;
	/** Indicate a tank is at a given location */
	public static final int TANK = 5;
	
	/** The cost of moving horizontally or vertically under normal conditions */
	public static final int HORIZONTAL_MOVEMENT_COST = 10;
	/** The cost of moving diagonally under normal conditions */
	public static final int DIAGONAL_MOVEMENT_COST = 14;
	
	
	/**
	 * Get the height of the map.
	 * 
	 * @return int
	 */
	public int getHeight();
	
	/**
	 * Get the width of the map.
	 * 
	 * @return int
	 */
	public int getWidth();
	
	/**
	 * Returns if the specified unit can enter the specified tile
	 * 
	 * @param mover The Unit that attempts to enter tile
	 * @param x The x coordinate of the terrain tile to retrieve
	 * @param y The y coordinate of the terrain tile to retrieve
	 * @return boolean specifying if the Unit can enter the tile
	 */
	public boolean isBlocked(Mover mover,int xs,int xy, int x, int y);
	
	/**
	 * Get the terrain type at a given location (TERRAIN_GRASS etc.)
	 * 
	 * @param x The x coordinate of the terrain tile to retrieve
	 * @param y The y coordinate of the terrain tile to retrieve
	 * @return The terrain tile at the given location
	 */
	public int getTerrain(int x, int y);
	
	/**
	 * Clear the array marking which tiles have been visted by the path 
	 * finder.
	 */
	public void clearVisited();
	
	/**
	 * Returns if the particular map tile has been visited by the Heuristics
	 */
	public boolean isVisited(int x, int y);
	
	/**
	 * Notification that the path finder visited a given tile. This is 
	 * used for debugging new heuristics.
	 * 
	 * @param x The x coordinate of the tile that was visited
	 * @param y The y coordinate of the tile that was visited
	 */
	public void setVisited(int x, int y);
	

}
