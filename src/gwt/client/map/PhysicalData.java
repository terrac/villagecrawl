package gwt.client.map;

import java.io.Serializable;

import gwt.client.main.Point;

public class PhysicalData implements IPhysical, Serializable{
	@Override
	public String toString() {
		return "PhysicalData [x=" + x + ", y=" + y + "]";
	}
	int x,y;
	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point(x,y);
	}
	public void setParent(MapData fullMapData, int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	

}
