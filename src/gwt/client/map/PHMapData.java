package gwt.client.map;

import com.googlecode.objectify.annotation.Serialized;

import gwt.client.main.IFullMapExecute;
import gwt.client.main.Person;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;

public class PHMapData extends HMapData implements IPhysical,IAge {
	
	
	public Point getPosition() {
		return ((IPhysical) get(VConstants.physicaldata)).getPosition();
	}
	public void setParent(MapData fullMapData, int x, int y) {
		
		IPhysical iPhysical = (IPhysical) get(VConstants.physicaldata);
		if(iPhysical == null){
			iPhysical = new PhysicalData();
			put(VConstants.physicaldata,iPhysical);
		}
		iPhysical.setParent(fullMapData, x, y);
		setParent(fullMapData);
		
	}
	public int getX() {
		return ((IPhysical) get(VConstants.physicaldata)).getX();
	}
	public void setX(int x) {
		((IPhysical) get(VConstants.physicaldata)).setX(x);
	}
	public int getY() {
		return ((IPhysical) get(VConstants.physicaldata)).getY();
	}
	public void setY(int y) {
		((IPhysical) get(VConstants.physicaldata)).setY(y);
	}
	@Override
	public boolean age() {

		return ((AgeData) get(VConstants.agedata)).age();
	}
	@Override
	public int getAge() {
		
		return ((AgeData) get(VConstants.agedata)).age;
	}
	@Override
	public void setAge(int age) {
		put(VConstants.agedata,age);
		
	}
	
	
}
