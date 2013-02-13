package gwt.client.game.util;

import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

public class PointBase extends PBase {

	public PointBase() {
	}

	public PointBase(int x, int y) {
		super(VConstants.xfull,x,VConstants.yfull,y);
	}
	public PointBase(Point p){
		this(p.x,p.y);
	}

	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof PointBase){
			PointBase pb = (PointBase) obj;
			return get(VConstants.xfull).equals(pb.get(VConstants.xfull))&&
					get(VConstants.yfull).equals(pb.get(VConstants.yfull));
		}
		return false;
	}

	public int getX() {
		return (Integer) get(VConstants.xfull);
	}

	public int getY() {
		return (Integer) get(VConstants.yfull);
	}
	
	public PBase clone() {
		return new PointBase().copyProperties(this);
	}
}
