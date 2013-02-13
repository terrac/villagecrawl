package gwt.client.map;

import java.io.Serializable;

import gwt.client.main.Point;

public class PhysicalMapData extends AgeMapData implements IPhysical{
	PhysicalData physicalData = new PhysicalData();
	public Point getPosition() {
		return physicalData.getPosition();
	}
	public void setParent(MapData fullMapData, int x, int y) {
		physicalData.setParent(fullMapData, x, y);
		setParent(fullMapData);
		
	}
	public int getX() {
		return physicalData.getX();
	}
	public void setX(int x) {
		physicalData.setX(x);
	}
	public int getY() {
		return physicalData.getY();
	}
	public void setY(int y) {
		physicalData.setY(y);
	}
	

}
