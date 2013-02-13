package gwt.client.map;

import gwt.client.main.Point;

public interface IPhysical {

	public Point getPosition();
	public void setParent(MapData fullMapData, int x, int y);
	public int getX() ;
	public void setX(int x);
	
	public int getY() ;
	public void setY(int y);
	

}
